package com.tserashkevich.services;

import com.tserashkevich.models.Order;
import com.tserashkevich.models.Reservation;
import com.tserashkevich.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public Reservation save(String date) {
        List<String> datesList = List.of(date.split(" - "));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Reservation reservation = new Reservation(
                LocalDate.parse(datesList.get(0), formatter),
                datesList.size() == 1 ? LocalDate.parse(datesList.get(0), formatter) : LocalDate.parse(datesList.get(1), formatter)
        );
        return reservationRepository.save(reservation);
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAllByItem() {
        return reservationRepository.findAll();
    }

}
