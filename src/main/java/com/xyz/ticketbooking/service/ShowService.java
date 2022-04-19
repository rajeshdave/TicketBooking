package com.xyz.ticketbooking.service;

import com.xyz.ticketbooking.model.Show;

import java.util.List;

public interface ShowService {
    public List<Show> searchShows(String city,
                                  String movie,
                                  String date);

    public List<Show> findAll();

    public Show getById(int id);

    public void saveOrUpdate(Show show);

    public void delete(int id);
}
