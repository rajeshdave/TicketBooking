package com.xyz.ticketbooking.repository;

import com.xyz.ticketbooking.model.Theatre;
import org.springframework.data.repository.CrudRepository;

/**
 * Represent the JPA Repository for Theatre.
 */
public interface TheatreRepository extends CrudRepository<Theatre, Integer> {
}
