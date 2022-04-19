package com.xyz.ticketbooking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represent the request dto for Controller.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowRequest {
    private int id;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;
    private int theatreScreenId;
    private int movieId;
}
