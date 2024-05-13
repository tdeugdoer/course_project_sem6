package com.tserashkevich.controllers;

import com.tserashkevich.models.Reservation;
import com.tserashkevich.repositories.UserRepository;
import com.tserashkevich.services.OrderService;
import com.tserashkevich.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "admin/orders/index";
    }

    @PostMapping("/new")
    public String create(@RequestParam("item_id") Long itemId, @RequestParam("date") String date) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        orderService.save(date, authentication.getName(), itemId);
        return "redirect:/catalog";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        orderService.delete(id);
        return "redirect:/orders";
    }
}
