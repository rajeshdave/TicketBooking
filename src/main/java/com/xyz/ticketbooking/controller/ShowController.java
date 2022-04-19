package com.xyz.ticketbooking.controller;

import com.xyz.ticketbooking.common.TicketBookingMessages;
import com.xyz.ticketbooking.model.Show;
import com.xyz.ticketbooking.model.ShowRequest;
import com.xyz.ticketbooking.service.MovieService;
import com.xyz.ticketbooking.service.ShowService;
import com.xyz.ticketbooking.service.TheatreScreenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for Show.
 * 1. We can browse theatres currently running the show (movie selected) in the town, including show timing by a chosen date.
 * 2. Theatres can create, update, and delete shows for the day.
 */
@RestController
@RequestMapping(value = "/show")
@Slf4j
class ShowController {

    @Autowired
    private ShowService showService;
    @Autowired
    private TheatreScreenService theatreScreenService;
    @Autowired
    private MovieService movieService;

    /**
     * We can browse theatres currently running the show (movie selected) in the town, including show timing by a chosen date.
     */
    @GetMapping(value = "/search")
    private List<Show> searchShows(@RequestParam(value = "city", required = false) String city,
                                  @RequestParam(value = "movie", required = false) String movie,
                                  @RequestParam(value = "date", required = false) String date) {
        log.info("Processing getRunningShows request for city = {}, movie = {}, and date = {}", city, movie, date);
        validate(city, movie, date);
        return showService.searchShows(city, movie, date);
    }

    @GetMapping("/all")
    private List<Show> getAllShows() {
        log.info("Processing getAllShows.");
        return showService.findAll();
    }

    @GetMapping("/{id}")
    private Show getShow(@PathVariable("id") int id) {
        log.info("Processing getShow for id = " + id);
        validateId(id);
        return showService.getById(id);
    }

    @PostMapping("/")
    private int createShow(@RequestBody ShowRequest showRequest) {
        log.info("Processing createShow for showRequest = " + showRequest);
        if (showRequest.getId() != 0) {
            throw new IllegalArgumentException(TicketBookingMessages.INVALID_VALUE_FOR_ID + showRequest.getId());
        }

        Show show = buildShow(showRequest);
        showService.saveOrUpdate(show);
        return show.getId();
    }

    @PutMapping("/{id}")
    private Show updateShow(@RequestBody ShowRequest showRequest, @PathVariable("id") int id) {
        log.info("Processing updateShow for showRequest = " + showRequest);
        validateId(id);
        showRequest.setId(id);
        Show show = buildShow(showRequest);
        showService.saveOrUpdate(show);
        return show;
    }

    private Show buildShow(ShowRequest showRequest) {
        Show show = new Show();
        show.setId(showRequest.getId());
        show.setFromTime(showRequest.getFromTime());
        show.setToTime(showRequest.getToTime());
        show.setMovie(movieService.getById(showRequest.getMovieId()));
        show.setTheatreScreen(theatreScreenService.getById(showRequest.getTheatreScreenId()));
        return show;
    }

    @DeleteMapping("/{id}")
    private String deleteShow(@PathVariable("id") int id) {
        log.info("Processing deleteShow for id = " + id);
        validateId(id);
        showService.delete(id);
        return TicketBookingMessages.SHOW_IS_DELETED_SUCCESSFULLY;
    }

    private void validateId(int id) {
        if (id < 1) {
            throw new IllegalArgumentException(TicketBookingMessages.INVALID_VALUE_FOR_ID + id);
        }
    }

    private void validate(String city, String movie, String date) {
        if (Strings.isEmpty(city)) {
            throw new IllegalArgumentException(TicketBookingMessages.REQUEST_PARAMETER_CITY_IS_MANDATORY);
        }

        if (Strings.isEmpty(movie)) {
            throw new IllegalArgumentException(TicketBookingMessages.REQUEST_PARAMETER_MOVIE_IS_MANDATORY);
        }

        if (Strings.isEmpty(date)) {
            throw new IllegalArgumentException(TicketBookingMessages.REQUEST_PARAMETER_DATE_IS_MANDATORY);
        }
    }
}
