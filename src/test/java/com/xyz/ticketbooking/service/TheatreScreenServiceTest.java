package com.xyz.ticketbooking.service;

import com.xyz.ticketbooking.model.Theatre;
import com.xyz.ticketbooking.model.TheatreScreen;
import com.xyz.ticketbooking.repository.TheatreScreenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TheatreScreenServiceTest {
    @Autowired
    TheatreScreenService theatreScreenService;

    @MockBean
    TheatreScreenRepository theatreScreenRepository;

    @Test
    public void testGetById() {
        Theatre theatre = new Theatre(1, "INOX", "Ahmedabad", "Gota, Ahmedabad");
        TheatreScreen theatreScreen = new TheatreScreen(1, "Screen-1", 100, theatre);
        when(theatreScreenRepository.findById(1)).thenReturn(Optional.of(theatreScreen));

        //test
        TheatreScreen theatreScreen1 = theatreScreenService.getById(1);

        assertEquals(1, theatreScreen1.getId());
        assertEquals("Screen-1", theatreScreen1.getName());
        verify(theatreScreenRepository, times(1)).findById(1);
    }

}
