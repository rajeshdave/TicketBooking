package com.xyz.ticketbooking.repository;

import com.xyz.ticketbooking.model.Movie;
import com.xyz.ticketbooking.model.Show;
import com.xyz.ticketbooking.model.Theatre;
import com.xyz.ticketbooking.model.TheatreScreen;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MovieRepositoryTest {
    @Autowired
    MovieRepository movieRepository;

    @BeforeEach
    public void setup() {
        movieRepository.deleteAll();
        Movie movie = new Movie(1, "RRR", "Hindi", "Jr. NTR, Ram Charan, Alia Bhatt");
        Theatre theatre = new Theatre(1, "INOX", "Ahmedabad", "Gota, Ahmedabad");
        TheatreScreen theatreScreen = new TheatreScreen(1, "Screen-1", 100, theatre);
        Show show = new Show(1, LocalDateTime.of(2022, 4, 5, 9, 0),
                LocalDateTime.of(2022, 4, 5, 11, 30), theatreScreen, movie);
    }

    @Test
    public void testCreateReadDelete() {
        Movie movie = new Movie(0, "Test Movie", "Hindi", "Jr. NTR, Ram Charan, Alia Bhatt");

        Movie movie1 = movieRepository.save(movie);
        Movie movie2 = movieRepository.findById(movie1.getId()).get();

        Assertions.assertThat(findMovie("Test Movie")).isTrue();
        Assertions.assertThat(movie2.getId()).isEqualTo(movie2.getId());

        movieRepository.deleteAll();

        Assertions.assertThat(findMovie("Test Movie")).isFalse();
    }

    private boolean findMovie(String movieName) {
        List<Movie> movieList = new ArrayList<>();
        movieRepository.findAll().forEach(m -> movieList.add(m));
        return movieList.stream().anyMatch(m -> m.getName().equals(movieName));
    }

}
