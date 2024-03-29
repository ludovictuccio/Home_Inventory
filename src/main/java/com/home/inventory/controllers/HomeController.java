package com.home.inventory.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(final Model model) {
        return "home";
    }

    @GetMapping("/admin/home")
    public String adminHome(final Model model) {
        return "redirect:/categories/list";
    }

}
