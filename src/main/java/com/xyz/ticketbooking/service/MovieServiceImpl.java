package com.xyz.ticketbooking.service;

import com.xyz.ticketbooking.exception.ResourceNotFoundException;
import com.xyz.ticketbooking.model.Movie;
import com.xyz.ticketbooking.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie getById(int id) {
        return movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TheatreScreen not found for id " + id));
    }
}
