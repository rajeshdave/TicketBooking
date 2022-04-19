package com.xyz.ticketbooking;

import com.xyz.ticketbooking.exception.ErrorCodes;
import com.xyz.ticketbooking.exception.ErrorResponse;
import com.xyz.ticketbooking.model.Show;
import com.xyz.ticketbooking.model.ShowRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The end to end Integration test by using TestRestTemplate.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class TicketBookingApplicationIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ApplicationContext context;

    @BeforeEach
    public void setup() {
        TicketBookingApplication.insertSampleDataInDatabase(context);
    }

    @Test
    public void testFindAllShowsSuccessfulResponse() {
        List<Show> showList = restTemplate.getForObject("/show/all", List.class);
        Assertions.assertFalse(showList.isEmpty());
        log.info(">>> showList: " + showList);
    }

    @Test
    public void testSearchShowsSuccessfulResponse() {
        List<Show> showList = restTemplate.getForObject("/show/search?city=Pune&movie=RRR&date=2022-04-05", List.class);
        Assertions.assertFalse(showList.isEmpty());
        log.info(">>> showList: " + showList);
    }

    @Test
    public void testSearchShowsEmptySuccessfulResponse() {
        List<Show> showList = restTemplate.getForObject("/show/search?city=Delhi&movie=RRR&date=2022-04-05", List.class);
        Assertions.assertTrue(showList.isEmpty());
        log.info(">>> showList: " + showList);
    }

    @Test
    public void testFindShowByIdSuccessfulResponse() {
        Show show = restTemplate.getForObject("/show/1", Show.class);
        Assertions.assertNotNull(show);
        log.info(">>> Show: " + show);
    }

    @Test
    public void testFindShowByIdErrorResponse() {
        ErrorResponse errorResponse = restTemplate.getForObject("/show/111", ErrorResponse.class);
        Assertions.assertNotNull(errorResponse);
        Assertions.assertEquals(errorResponse.getErrorCode(), ErrorCodes.TICKET_BOOKING_CUSTOM_ERROR_001);
        Assertions.assertEquals(errorResponse.getMessage(), "Show not found for id 111");
    }

    @Test
    public void testCreateShow() {
        ShowRequest showRequest = new ShowRequest(0, LocalDateTime.of(2022, 4, 5, 9, 0),
                LocalDateTime.of(2022, 4, 5, 11, 30), 1, 1);

        Integer id = restTemplate.postForObject("/show/", showRequest, Integer.class);
        Assertions.assertNotNull(id);
        log.info(">>> id: " + id);

        Show show = restTemplate.getForObject("/show/" + id, Show.class);
        Assertions.assertNotNull(show);
        log.info(">>> Show: " + show);
    }

    @Test
    public void testUpdateShow() {
        ShowRequest showRequest = new ShowRequest(1, LocalDateTime.of(2022, 4, 5, 9, 0),
                LocalDateTime.of(2022, 4, 5, 11, 30), 2, 2);

        restTemplate.put("/show/1", showRequest);

        Show show = restTemplate.getForObject("/show/1", Show.class);
        Assertions.assertNotNull(show);
        Assertions.assertEquals(show.getId(), 1);
        Assertions.assertEquals(show.getMovie().getId(), 2);
        Assertions.assertEquals(show.getTheatreScreen().getId(), 2);
        log.info(">>> Show: " + show);
    }

    @Test
    public void testDeleteShow() {
        Show show = restTemplate.getForObject("/show/1", Show.class);
        Assertions.assertNotNull(show);
        log.info(">>> Show: " + show);

        restTemplate.delete("/show/1", Show.class);

        ErrorResponse errorResponse = restTemplate.getForObject("/show/1", ErrorResponse.class);
        Assertions.assertNotNull(errorResponse);
        Assertions.assertEquals(errorResponse.getErrorCode(), ErrorCodes.TICKET_BOOKING_CUSTOM_ERROR_001);
        Assertions.assertEquals(errorResponse.getMessage(), "Show not found for id 1");
    }

}
