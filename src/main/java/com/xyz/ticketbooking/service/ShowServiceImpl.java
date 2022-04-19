package com.xyz.ticketbooking.service;

import com.xyz.ticketbooking.common.TicketBookingMessages;
import com.xyz.ticketbooking.exception.ResourceNotFoundException;
import com.xyz.ticketbooking.model.Show;
import com.xyz.ticketbooking.repository.ShowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
class ShowServiceImpl implements ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Override
    public List<Show> searchShows(String city,
                                  String movie,
                                  String date) {
        log.debug("Searching shows for city = {}, movie = {}, and date = {}", city, movie, date);
        LocalDate localDate = parseDate(date);
        return showRepository.searchShows(city, movie, localDate);
    }

    private LocalDate parseDate(String date) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        } catch (Exception e) {
            throw new IllegalArgumentException(TicketBookingMessages.INVALID_DATE_FORMAT);
        }
        return localDate;
    }

    @Override
    public List<Show> findAll() {
        log.debug("Finding all shows.");
        List<Show> allShows = new ArrayList();
        showRepository.findAll().forEach(s -> allShows.add(s));
        return allShows;
    }

    @Override
    public Show getById(int id) {
        log.debug("Finding show by Id = " + id);
        return showRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Show not found for id " + id));
    }

    @Override
    public void saveOrUpdate(Show show) {
        log.debug("Saving show for id " + show.getId());
        showRepository.save(show);
    }

    @Override
    public void delete(int id) {
        log.debug("Deleting show by Id = " + id);
        showRepository.deleteById(id);
    }
}
