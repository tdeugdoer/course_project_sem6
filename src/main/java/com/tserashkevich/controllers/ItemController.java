package com.tserashkevich.controllers;

import com.tserashkevich.models.Comment;
import com.tserashkevich.models.Item;
import com.tserashkevich.services.CommentService;
import com.tserashkevich.services.ItemService;
import com.tserashkevich.util.ItemValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;
    private final ItemValidator itemValidator;
    private final CommentService commentService;

    @GetMapping("/new")
    public String newItem(@ModelAttribute("item") Item item) {
        return "admin/item/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("item") @Valid Item item,
                         BindingResult bindingResult,
                         @RequestParam("file") MultipartFile file) {
        itemValidator.validate(item, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/item/new";
        }

        itemService.save(item, file);
        return "redirect:/catalog";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model item, @PathVariable("id") Long id) {
        item.addAttribute("item", itemService.findOne(id));
        return "admin/item/edit";
    }

    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("item") @Valid Item item,
                         BindingResult bindingResult,
                         @PathVariable("id") Long id,
                         @RequestParam("file") MultipartFile file) {
        itemValidator.validate(item, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/item/edit";
        }

        itemService.update(id, item, file);
        return "redirect:/catalog";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        itemService.delete(id);
        return "redirect:/catalog";
    }

    @GetMapping("/info/{id}")
    public String itemInfo(Model item,
                           Model comments,
                           Model reservations,
                           @PathVariable("id") Long id,
                           @ModelAttribute("comment") Comment comment) {
        item.addAttribute("item", itemService.findOne(id));
        comments.addAttribute("comments", commentService.findCommentByItem(id));
        return "common/itemPage";
    }
}
