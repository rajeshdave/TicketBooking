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

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ShowRepositoryTest {
    @Autowired
    ShowRepository showRepository;
    @Autowired
    TheatreRepository theatreRepository;
    @Autowired
    TheatreScreenRepository theatreScreenRepository;
    @Autowired
    MovieRepository movieRepository;

    private Show show;

    @BeforeEach
    public void setup() {
        showRepository.deleteAll();
        movieRepository.deleteAll();
        theatreRepository.deleteAll();
        theatreScreenRepository.deleteAll();

    }

    @Test
    public void testCreateAndFindById() {
        Movie movie = new Movie(1, "RRR", "Hindi", "Jr. NTR, Ram Charan, Alia Bhatt");
        Theatre theatre = new Theatre(1, "INOX", "Ahmedabad", "Gota, Ahmedabad");
        TheatreScreen theatreScreen = new TheatreScreen(1, "Screen-1", 100, theatre);
        show = new Show(1, LocalDateTime.of(2022, 4, 5, 9, 0),
                LocalDateTime.of(2022, 4, 5, 11, 30), theatreScreen, movie);

        movieRepository.save(movie);
        theatreRepository.save(theatre);
        theatreScreenRepository.save(theatreScreen);

        Show show1 = showRepository.save(show);
        Show show2 = showRepository.findById(show1.getId()).get();

        Assertions.assertThat(show2.getId()).isEqualTo(show2.getId());
    }

    @Test
    public void testDeleteAndFindAll() {
        Movie movie = new Movie(2, "RRR", "Hindi", "Jr. NTR, Ram Charan, Alia Bhatt");
        Theatre theatre = new Theatre(2, "INOX", "Ahmedabad", "Gota, Ahmedabad");
        TheatreScreen theatreScreen = new TheatreScreen(2, "Screen-1", 100, theatre);
        show = new Show(2, LocalDateTime.of(2022, 4, 5, 9, 0),
                LocalDateTime.of(2022, 4, 5, 11, 30), theatreScreen, movie);

        movieRepository.save(movie);
        theatreRepository.save(theatre);
        theatreScreenRepository.save(theatreScreen);

        Show show1 = showRepository.save(show);
        Assertions.assertThat(showRepository.findAll()).hasSize(1);

        showRepository.deleteById(show1.getId());

        Assertions.assertThat(showRepository.findById(show1.getId())).isEmpty();
    }

}
