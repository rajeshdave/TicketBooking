package com.xyz.ticketbooking.service;

import com.xyz.ticketbooking.model.Movie;
import com.xyz.ticketbooking.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MovieServiceTest {
    @Autowired
    MovieService movieService;

    @MockBean
    MovieRepository movieRepository;

    @Test
    public void testGetById() {
        Movie movie = new Movie(1, "RRR", "Hindi", "Jr. NTR, Ram Charan, Alia Bhatt");
        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));

        //test
        Movie movie1 = movieService.getById(1);

        assertEquals(1, movie1.getId());
        assertEquals("RRR", movie1.getName());
        verify(movieRepository, times(1)).findById(1);
    }

}
