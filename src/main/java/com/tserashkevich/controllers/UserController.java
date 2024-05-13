package com.tserashkevich.controllers;

import com.tserashkevich.services.OrderService;
import com.tserashkevich.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public String index(Model users) {
        users.addAttribute("users", userService.findAll());
        return "admin/users/index";
    }

    @GetMapping("/personal")
    public String personal(Model user, Model orders) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        user.addAttribute("user", userService.findUserByLogin(username).get());
        orders.addAttribute("orders", orderService.findAllByUser(username));
        return "user/userPage";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") Long id) {
        userService.changeRole(id);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
