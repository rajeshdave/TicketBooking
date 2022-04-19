package com.xyz.ticketbooking.repository;

import com.xyz.ticketbooking.model.Movie;
import org.springframework.data.repository.CrudRepository;

/**
 * Represent the JPA Repository for Movie.
 */
public interface MovieRepository extends CrudRepository<Movie, Integer> {
}
