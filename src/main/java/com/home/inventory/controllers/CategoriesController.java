package com.home.inventory.controllers;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.home.inventory.entities.Categories;
import com.home.inventory.repository.CategoriesRepository;
import com.home.inventory.services.CategoriesService;

@Controller
public class CategoriesController {

    private static final Logger LOGGER = LogManager
            .getLogger("CategoriesController");

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @GetMapping("/categories/list")
    public String home(final Model model) {
        model.addAttribute("categories", categoriesRepository.findAll());
        return "categories/list";
    }

    @GetMapping("/categories/get/{id}")
    public ModelAndView getCategory(@PathVariable("id") final Long id,
            final ModelMap model) {
        Categories category = categoriesService.getCategoryById(id);

        if (category == null) {
            LOGGER.error("Invalid category Id: {}", id);
            return new ModelAndView("categories/list");
        }
        model.addAttribute("categorySelected", category);
        return new ModelAndView("categories/get", model);
    }

    @GetMapping("/categories/add")
    public String addCategory(final Model model) {
        model.addAttribute("category", new Categories());
        return "categories/add";
    }

    @PostMapping("/categories/validate")
    public String validate(@Valid final Categories category,
            final BindingResult result, final Model model) {

        if (!result.hasErrors()) {
            Categories categoryToAdd = categoriesService.addCategory(category);
            model.addAttribute("category", categoryToAdd);
            return "redirect:/categories/list";
        }
        LOGGER.info("POST request FAILED for: /categories/validate");
        return "categories/add";
    }

    @GetMapping("/categories/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Long id,
            final Model model) {
        Categories category = categoriesService.getCategoryById(id);

        if (category == null) {
            LOGGER.error("Invalid category Id: {}", id);
            return "redirect:/categories/get";
        }
        model.addAttribute("category", category);
        return "categories/update";
    }

    @PostMapping("/categories/update/{id}")
    public String updateCategory(@PathVariable("id") final Long id,
            final Categories category, final BindingResult result,
            final Model model) {
        if (result.hasErrors()) {
            LOGGER.info("POST request FAILED for: /categories/update/{id}");
            return "categories/update";
        }
        category.setId(id);
        categoriesService.updateCategoryById(category, id);
        model.addAttribute("category", category);
        return "redirect:/categories/get/{id}";
    }

}
