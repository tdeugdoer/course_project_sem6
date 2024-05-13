package com.tserashkevich.services;

import com.tserashkevich.models.Item;
import com.tserashkevich.models.Order;
import com.tserashkevich.models.Reservation;
import com.tserashkevich.models.User;
import com.tserashkevich.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ReservationService reservationService;
    private final ItemService itemService;

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Order> findAllByUser(String userName) {
        Optional<User> user = userService.findUserByLogin(userName);
        return orderRepository.findAllByUser(user.get());
    }

    public void save(String date, String userName, Long itemId) {
        Reservation reservation = reservationService.save(date);
        Optional<User> user = userService.findUserByLogin(userName);
        Item item = itemService.findOne(itemId);
        Order order = new Order(user.get(), item, reservation);
        orderRepository.save(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
