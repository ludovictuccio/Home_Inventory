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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.home.inventory.entities.Categories;
import com.home.inventory.repository.CategoriesRepository;
import com.home.inventory.services.interfaces.ICategoriesService;

@Controller
@RequestMapping("/categories")
public class CategoriesController {

    private static final Logger LOGGER = LogManager
            .getLogger("CategoriesController");

    @Autowired
    private ICategoriesService categoriesService;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @GetMapping("/list")
    public String home(final Model model) {
        model.addAttribute("categories", categoriesRepository.findAll());
        return "categories/list";
    }

    @GetMapping("/add")
    public String addCategory(final Model model) {
        model.addAttribute("category", new Categories());
        return "categories/add";
    }

    @PostMapping("/validate")
    public String validate(
            @Valid @ModelAttribute("category") Categories category,
            final BindingResult result, final Model model) {
        if (!result.hasErrors()) {
            Categories categoryToAdd = categoriesService.addCategory(category);
            model.addAttribute("category", categoryToAdd);
            return "redirect:/categories/list";
        }
        LOGGER.info("POST request FAILED for: /categories/validate");
        return "categories/add";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id") final Long id,
            final Model model) {
        Categories category = categoriesService.getCategoryById(id);

        if (category == null) {
            LOGGER.error("Invalid category Id: {}", id);
            return "redirect:/categories/list";
        }
        model.addAttribute("category", category);
        return "categories/update";
    }

    @PostMapping("/update")
    public String updateCategory(@RequestParam("id") final Long id,
            @Valid @ModelAttribute("category") Categories category,
            final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            LOGGER.info("POST request FAILED for: /categories/update/{id}");
            return "categories/update";
        }
        category.setId(id);
        categoriesService.updateCategoryById(category, id);
        model.addAttribute("category", category);
        return "redirect:/categories/list";
    }

    @GetMapping("/delete")
    public String deleteCategory(@RequestParam("id") final Long id,
            final Model model) {
        categoriesService.deleteCategoryById(id);
        model.addAttribute("categories", categoriesRepository.findAll());
        return "redirect:/categories/list";
    }

}
