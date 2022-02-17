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

import com.home.inventory.entities.SousCategories;
import com.home.inventory.repository.SousCategoriesRepository;
import com.home.inventory.services.interfaces.ISousCategoriesService;

@Controller
@RequestMapping("/souscategories")
public class SousCategoriesController {

    private static final Logger LOGGER = LogManager
            .getLogger("SousCategoriesController");

    @Autowired
    private ISousCategoriesService sousCategoriesService;

    @Autowired
    private SousCategoriesRepository sousCategoriesRepository;

    @GetMapping("/list")
    public String home(final Model model) {
        model.addAttribute("souscategories",
                sousCategoriesRepository.findAll());
        return "souscategories/list";
    }

    @GetMapping("/add")
    public String addSousCategory(final Model model) {
        model.addAttribute("souscategory", new SousCategories());
        return "souscategories/add";
    }

    @PostMapping("/validate")
    public String validate(
            @Valid @ModelAttribute("souscategory") SousCategories souscategory,
            final BindingResult result, final Model model) {
        if (!result.hasErrors()) {
            SousCategories souscategoryToAdd = sousCategoriesService
                    .addSousCategory(souscategory);
            model.addAttribute("souscategory", souscategoryToAdd);
            return "redirect:/souscategories/list";
        }
        LOGGER.info("POST request FAILED for: /souscategories/validate");
        return "souscategories/add";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id") final Long id,
            final Model model) {
        SousCategories souscategory = sousCategoriesService
                .getSousCategoryById(id);

        if (souscategory == null) {
            LOGGER.error("Invalid sous-category Id: {}", id);
            return "redirect:/souscategories/list";
        }
        model.addAttribute("souscategory", souscategory);
        return "souscategories/update";
    }

    @PostMapping("/update")
    public String updateSousCategory(@RequestParam("id") final Long id,
            @Valid @ModelAttribute("souscategory") SousCategories souscategory,
            final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            LOGGER.info("POST request FAILED for: /souscategories/update/{id}");
            return "souscategories/update";
        }
        souscategory.setId(id);
        sousCategoriesService.updateSousCategoryById(souscategory, id);
        model.addAttribute("souscategory", souscategory);
        return "redirect:/souscategories/list";
    }

    @GetMapping("/delete")
    public String deleteSousCategory(@RequestParam("id") final Long id,
            final Model model) {
        sousCategoriesService.deleteSousCategoryById(id);
        model.addAttribute("souscategories",
                sousCategoriesRepository.findAll());
        return "redirect:/souscategories/list";
    }

}
