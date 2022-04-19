package com.xyz.ticketbooking.service;

import com.xyz.ticketbooking.exception.ResourceNotFoundException;
import com.xyz.ticketbooking.model.TheatreScreen;
import com.xyz.ticketbooking.repository.TheatreScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class TheatreScreenServiceImpl implements TheatreScreenService {

    @Autowired
    private TheatreScreenRepository theatreScreenRepository;

    @Override
    public TheatreScreen getById(int id) {
        return theatreScreenRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TheatreScreen not found for id " + id));
    }
}
