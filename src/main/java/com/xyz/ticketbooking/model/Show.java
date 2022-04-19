package com.xyz.ticketbooking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represent the Show running in any screen of any Theatre.
 * It contains Movie, TheatreScreen and start & end time of show.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;

    @ManyToOne
    private TheatreScreen theatreScreen;

    @ManyToOne
    private Movie movie;
}
