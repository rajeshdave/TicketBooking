package com.xyz.ticketbooking.repository;

import com.xyz.ticketbooking.model.TheatreScreen;
import org.springframework.data.repository.CrudRepository;

/**
 * Represent the JPA Repository for TheatreScreen.
 */
public interface TheatreScreenRepository extends CrudRepository<TheatreScreen, Integer> {
}
