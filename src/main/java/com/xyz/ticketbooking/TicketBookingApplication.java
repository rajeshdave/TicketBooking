package com.xyz.ticketbooking;

import com.xyz.ticketbooking.model.Movie;
import com.xyz.ticketbooking.model.Show;
import com.xyz.ticketbooking.model.Theatre;
import com.xyz.ticketbooking.model.TheatreScreen;
import com.xyz.ticketbooking.repository.MovieRepository;
import com.xyz.ticketbooking.repository.ShowRepository;
import com.xyz.ticketbooking.repository.TheatreRepository;
import com.xyz.ticketbooking.repository.TheatreScreenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This represents Spring boot Application class.
 */
@SpringBootApplication
@Slf4j
public class TicketBookingApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TicketBookingApplication.class, args);

        //Only for demo purpose. Just start the application and browse all running shows.
        log.info("Inserting Sample data.");
        insertSampleDataInDatabase(context);
        log.info("Sample data insertion is completed.");
    }

    /**
     * Setup the data in Database. This is just for demo purpose as we are using in-memory H2 database.
     * <p>
     * 1. Read Theatres and Movies added through data.sql file.
     * 2. Add TheatreScreens into each Theatre.
     * 3. Add Shows for each of the TheatreScreen.
     */
    static void insertSampleDataInDatabase(ApplicationContext context) {
        MovieRepository movieRepository = context.getBean(MovieRepository.class);
        List<Movie> movieList = new ArrayList<>();
        Iterable<Movie> movies = movieRepository.findAll();
        movies.forEach(movie -> {
                    log.info(movie.toString());
                    movieList.add(movie);
                }
        );

        TheatreRepository theatreRepository = context.getBean(TheatreRepository.class);
        ShowRepository showRepository = context.getBean(ShowRepository.class);
        Iterable<Theatre> theatres = theatreRepository.findAll();
        AtomicInteger tsIds = new AtomicInteger(1);
        AtomicInteger showIds = new AtomicInteger(1);
        theatres.forEach(theatre -> {
            log.info(theatre.toString());
            TheatreScreen theatreScreen1 = new TheatreScreen(tsIds.getAndIncrement(), "Screen-1", 100, theatre);
            TheatreScreen theatreScreen2 = new TheatreScreen(tsIds.getAndIncrement(), "Screen-2", 120, theatre);
            TheatreScreenRepository theatreScreenRepository = context.getBean(TheatreScreenRepository.class);
            theatreScreenRepository.save(theatreScreen1);
            theatreScreenRepository.save(theatreScreen2);

            Show show1ForTheatreScreen1 = new Show(showIds.getAndIncrement(), LocalDateTime.of(2022, 4, 5, 9, 0),
                    LocalDateTime.of(2022, 4, 5, 11, 30), theatreScreen1, movieList.get(0));
            Show show2ForTheatreScreen1 = new Show(showIds.getAndIncrement(), LocalDateTime.of(2022, 4, 5, 13, 0),
                    LocalDateTime.of(2022, 4, 5, 15, 30), theatreScreen1, movieList.get(0));
            Show show3ForTheatreScreen1 = new Show(showIds.getAndIncrement(), LocalDateTime.of(2022, 4, 5, 13, 0),
                    LocalDateTime.of(2022, 4, 5, 15, 30), theatreScreen1, movieList.get(0));


            Show show1ForTheatreScreen2 = new Show(showIds.getAndIncrement(), LocalDateTime.of(2022, 4, 5, 9, 0),
                    LocalDateTime.of(2022, 4, 5, 11, 30), theatreScreen2, movieList.get(1));
            Show show2ForTheatreScreen2 = new Show(showIds.getAndIncrement(), LocalDateTime.of(2022, 4, 5, 13, 0),
                    LocalDateTime.of(2022, 4, 5, 15, 30), theatreScreen2, movieList.get(1));
            Show show3ForTheatreScreen2 = new Show(showIds.getAndIncrement(), LocalDateTime.of(2022, 4, 5, 13, 0),
                    LocalDateTime.of(2022, 4, 5, 15, 30), theatreScreen2, movieList.get(1));

            showRepository.save(show1ForTheatreScreen1);
            showRepository.save(show2ForTheatreScreen1);
            showRepository.save(show3ForTheatreScreen1);
            showRepository.save(show1ForTheatreScreen2);
            showRepository.save(show2ForTheatreScreen2);
            showRepository.save(show3ForTheatreScreen2);


        });

        Iterable<Show> allShows = showRepository.findAll();
        allShows.forEach(show -> log.info(show.toString()));
    }

}
