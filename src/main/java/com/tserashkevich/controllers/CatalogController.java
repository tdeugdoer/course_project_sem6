package com.tserashkevich.controllers;

import com.tserashkevich.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/catalog")
public class CatalogController {
    private final ItemService itemService;

    @GetMapping
    public String index(Model items) {
        items.addAttribute("items", itemService.findAll());
        return "common/catalog";
    }
}
