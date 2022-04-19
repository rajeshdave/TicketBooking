package com.xyz.ticketbooking;

import com.xyz.ticketbooking.service.ShowService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Testing if Application load Correctly.
 */
@SpringBootTest
class TicketBookingApplicationTests {

    @Autowired
    ShowService showService;

    @Test
    void testContextLoads() {
        Assertions.assertThat(showService).isNotNull();
    }

}
