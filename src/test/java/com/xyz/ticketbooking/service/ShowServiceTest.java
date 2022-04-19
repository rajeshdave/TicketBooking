package com.xyz.ticketbooking.service;

import com.xyz.ticketbooking.common.TicketBookingMessages;
import com.xyz.ticketbooking.model.Movie;
import com.xyz.ticketbooking.model.Show;
import com.xyz.ticketbooking.model.Theatre;
import com.xyz.ticketbooking.model.TheatreScreen;
import com.xyz.ticketbooking.repository.ShowRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ShowServiceTest {
    @Autowired
    ShowService showService;

    @MockBean
    ShowRepository showRepository;

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
    public void testFindAllShows() {
        when(showRepository.findAll()).thenReturn(showList);

        //test
        List<Show> showList = showService.findAll();

        assertEquals(1, showList.size());
        verify(showRepository, times(1)).findAll();
    }

    @Test
    public void testSearchShows() {
        when(showRepository.searchShows(anyString(), anyString(), any())).thenReturn(showList);

        //test
        List<Show> showList = showService.searchShows("Ahmedabad", "RRR", "2022-04-05");

        assertEquals(1, showList.size());
        verify(showRepository, times(1)).searchShows(anyString(), anyString(), any());
    }

    @Test
    public void testSearchShowsInvalidDateFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            showService.searchShows("Ahmedabad", "RRR", "2022/04/05");
        });

        assertEquals(exception.getMessage(), TicketBookingMessages.INVALID_DATE_FORMAT);
    }

    @Test
    public void testGetById() {
        when(showRepository.findById(1)).thenReturn(Optional.of(showList.get(0)));

        //test
        Show show1 = showService.getById(1);

        assertEquals(1, show1.getId());
        assertEquals(1, show1.getMovie().getId());
        assertEquals("RRR", show1.getMovie().getName());
        assertEquals(1, show1.getTheatreScreen().getId());
        verify(showRepository, times(1)).findById(1);
    }

    @Test
    public void testSaveOrUpdate() {
        Show show = showList.get(0);
        showService.saveOrUpdate(show);

        verify(showRepository, times(1)).save(show);
    }

    @Test
    public void testDelete() {
        showService.delete(1);

        verify(showRepository, times(1)).deleteById(1);
    }

}
