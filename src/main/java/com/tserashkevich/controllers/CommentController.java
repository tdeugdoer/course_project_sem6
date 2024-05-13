package com.tserashkevich.controllers;

import com.tserashkevich.models.Comment;
import com.tserashkevich.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/new")
    public String create(@ModelAttribute("comment") Comment comment,
                         @RequestParam("item_id") Long itemId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        commentService.save(comment, authentication.getName(), itemId);
        return "redirect:/catalog";
    }
}
