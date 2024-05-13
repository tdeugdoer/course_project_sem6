package com.tserashkevich.services;

import com.tserashkevich.models.Reservation;
import com.tserashkevich.repositories.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationService = new ReservationService(reservationRepository);
    }

    @Test
    void save_ShouldSaveReservation() {
        // Arrange
        Reservation reservation = new Reservation();

        // Act
        reservationService.save(reservation);

        // Assert
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void findAllByItem_ShouldReturnListOfReservations() {
        // Arrange
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());
        reservations.add(new Reservation());

        when(reservationRepository.findAll()).thenReturn(reservations);

        // Act
        List<Reservation> result = reservationService.findAllByItem();

        // Assert
        assertEquals(reservations, result);
        verify(reservationRepository, times(1)).findAll();
    }
}