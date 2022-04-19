package com.xyz.ticketbooking.repository;

import com.xyz.ticketbooking.model.Show;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Represent the JPA Repository for Show.
 */
public interface ShowRepository extends CrudRepository<Show, Integer> {

    @Query("SELECT DISTINCT s FROM Show s " +
            "JOIN s.movie m " +
            "JOIN s.theatreScreen ts " +
            "JOIN ts.theatre t " +
            "WHERE t.city = :city " +
            "And m.name LIKE %:movie% " +
            "And CAST(s.fromTime AS date) = CAST(:date AS date)")
    public List<Show> searchShows(String city,
                                  String movie,
                                  LocalDate date);
}
