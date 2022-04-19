package com.xyz.ticketbooking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyz.ticketbooking.common.TicketBookingMessages;
import com.xyz.ticketbooking.exception.ErrorCodes;
import com.xyz.ticketbooking.exception.ResourceNotFoundException;
import com.xyz.ticketbooking.model.*;
import com.xyz.ticketbooking.service.MovieService;
import com.xyz.ticketbooking.service.ShowService;
import com.xyz.ticketbooking.service.TheatreScreenService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for ShowController.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(ShowController.class)
public class ShowControllerTest {
    @MockBean
    ShowService showService;

    @MockBean
    TheatreScreenService theatreScreenService;

    @MockBean
    MovieService movieService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    private static List<Show> showList;

    @BeforeAll
    public static void setup() {
        Movie movie = new Movie(1, "RRR", "Hindi", "Jr. NTR, Ram Charan, Alia Bhatt");
        Theatre theatre = new Theatre(1, "INOX", "Ahmedabad", "Gota, Ahmedabad");
        TheatreScreen theatreScreen = new TheatreScreen(1, "Screen-1", 100, theatre);
        Show show = new Show(1, LocalDateTime.of(2022, 4, 5, 9, 0),
                LocalDateTime.of(2022, 4, 5, 11, 30), theatreScreen, movie);

        showList = Arrays.asList(show);
    }

    @Test
    public void testFindAll() throws Exception {
        Mockito.when(showService.findAll()).thenReturn(showList);

        mockMvc.perform(get("/show/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)));
    }

    @Test
    public void testSearchShows() throws Exception {
        Mockito.when(showService.searchShows(anyString(), anyString(), anyString())).thenReturn(showList);

        mockMvc.perform(get("/show/search?city=Pune&movie=RRR&date=2022-04-05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)));
    }

    @Test
    public void testSearchShowsForInvalidArgument() throws Exception {
        Mockito.when(showService.searchShows(anyString(), anyString(), anyString())).thenReturn(showList);

        mockMvc.perform(get("/show/search"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", Matchers.is(ErrorCodes.TICKET_BOOKING_BAD_REQUEST_ERROR_001)));
    }

    @Test
    public void testGetShow() throws Exception {
        Mockito.when(showService.getById(1)).thenReturn(showList.get(0));

        mockMvc.perform(get("/show/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.theatreScreen.id", Matchers.is(1)))
                .andExpect(jsonPath("$.movie.id", Matchers.is(1)))
                .andExpect(jsonPath("$.movie.name", Matchers.is("RRR")));
    }

    @Test
    public void testGetShowForNotFound() throws Exception {
        Mockito.when(showService.getById(111)).thenThrow(new ResourceNotFoundException("Test Error message."));

        mockMvc.perform(get("/show/111"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode", Matchers.is(ErrorCodes.TICKET_BOOKING_CUSTOM_ERROR_001)))
                .andExpect(jsonPath("$.message", Matchers.is("Test Error message.")));
    }

    @Test
    public void testGetShowForInvalidId() throws Exception {
        mockMvc.perform(get("/show/0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", Matchers.is(ErrorCodes.TICKET_BOOKING_BAD_REQUEST_ERROR_001)));
    }

    @Test
    public void testCreateShow() throws Exception {
        Movie movie = new Movie(1, "RRR", "Hindi", "Jr. NTR, Ram Charan, Alia Bhatt");
        Theatre theatre = new Theatre(1, "INOX", "Ahmedabad", "Gota, Ahmedabad");
        TheatreScreen theatreScreen = new TheatreScreen(1, "Screen-1", 100, theatre);

        ShowRequest showRequest = new ShowRequest(0, LocalDateTime.of(2022, 4, 5, 9, 0),
                LocalDateTime.of(2022, 4, 5, 11, 30), 1, 1);

        Mockito.when(movieService.getById(1)).thenReturn(movie);
        Mockito.when(theatreScreenService.getById(1)).thenReturn(theatreScreen);

        mockMvc.perform(post("/show/").content(mapper.writeValueAsString(showRequest)).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(0)));
    }

    @Test
    public void testUpdateShow() throws Exception {
        Movie movie = new Movie(1, "RRR-Updated", "Hindi", "Jr. NTR, Ram Charan, Alia Bhatt");
        Theatre theatre = new Theatre(1, "INOX", "Ahmedabad", "Gota, Ahmedabad");
        TheatreScreen theatreScreen = new TheatreScreen(1, "Screen-1", 100, theatre);

        ShowRequest showRequest = new ShowRequest(1, LocalDateTime.of(2022, 4, 5, 9, 0),
                LocalDateTime.of(2022, 4, 5, 11, 30), 1, 1);

        Mockito.when(movieService.getById(1)).thenReturn(movie);
        Mockito.when(theatreScreenService.getById(1)).thenReturn(theatreScreen);

        mockMvc.perform(put("/show/1").content(mapper.writeValueAsString(showRequest)).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.theatreScreen.id", Matchers.is(1)))
                .andExpect(jsonPath("$.movie.id", Matchers.is(1)))
                .andExpect(jsonPath("$.movie.name", Matchers.is("RRR-Updated")));
    }

    @Test
    public void testDeleteShow() throws Exception {
        Mockito.when(showService.getById(1)).thenReturn(showList.get(0));

        mockMvc.perform(delete("/show/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(TicketBookingMessages.SHOW_IS_DELETED_SUCCESSFULLY)));
    }

    @Test
    public void testDeleteShowForInvalidId() throws Exception {
        mockMvc.perform(delete("/show/0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", Matchers.is(ErrorCodes.TICKET_BOOKING_BAD_REQUEST_ERROR_001)));
    }
}
