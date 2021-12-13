package com.home.inventory.controllers;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.home.inventory.entities.User;
import com.home.inventory.repository.UserRepository;
import com.home.inventory.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger("UserController");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String home(final Model model) {
        model.addAttribute("users", userService.findAllUsers());
        LOGGER.info("GET request SUCCESS for: /user/list");
        return "user/list";
    }

    @GetMapping("/add")
    public String addUser(final Model model) {
        model.addAttribute("user", new User());
        LOGGER.info("GET request SUCCESS for: /user/add");
        return "user/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("user") final User user,
            final BindingResult result, final Model model) {

        if (!result.hasErrors()) {
            User userToSave = userService.saveUser(user);
            model.addAttribute("user", userToSave);
            LOGGER.info("POST request SUCCESS for: /user/validate");
            return "redirect:/user/list";
        }
        LOGGER.info("POST request FAILED for: /user/validate");
        return "user/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Long id,
            final Model model) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            LOGGER.error("Invalid user Id: {}", id);
            LOGGER.info("GET request FAILED for: /user/update/{id}");
            return "redirect:/user/list";
        }
        model.addAttribute("user", user);
        LOGGER.info("GET request SUCCESS for: /user/update/{id}");
        return "user/update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") final Long id,
            @Valid @ModelAttribute("user") final User user,
            final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            LOGGER.info("POST request FAILED for: /user/update/{id}");
            return "user/update";
        }
        user.setId(id);
        userService.updateUser(user);
        model.addAttribute("user", user);
        LOGGER.info("POST request SUCCESS for: /user/update/{id}");
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") final Long id,
            final Model model) {
        try {
            userRepository.deleteById(id);
            model.addAttribute("users", userService.findAllUsers());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Invalid user Id: {}", id);
            LOGGER.info("GET request FAILED for: /user/delete/{id}");
        }
        LOGGER.info("GET request SUCCESS for: /user/delete/{id}");
        return "redirect:/user/list";
    }
}
