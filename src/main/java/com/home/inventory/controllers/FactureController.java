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

import com.home.inventory.entities.Facture;
import com.home.inventory.repository.FactureRepository;
import com.home.inventory.services.IFactureService;

@Controller
@RequestMapping("/factures")
public class FactureController {

    private static final Logger LOGGER = LogManager
            .getLogger("FactureController");

    @Autowired
    private IFactureService factureService;

    @Autowired
    private FactureRepository factureRepository;

    @GetMapping("/list")
    public String home(final Model model) {
        model.addAttribute("factures", factureRepository.findAll());
        return "factures/list";
    }

    @GetMapping("/add")
    public String addFacture(final Model model) {
        model.addAttribute("facture", new Facture());
        return "factures/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("facture") Facture facture,
            final BindingResult result, final Model model) {
        if (!result.hasErrors()) {
            Facture factureToAdd = factureService.addFacture(facture);
            model.addAttribute("facture", factureToAdd);
            return "redirect:/factures/list";
        }
        LOGGER.info("POST request FAILED for: /factures/validate");
        return "factures/add";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id") final Long id,
            final Model model) {
        Facture facture = factureService.getFactureById(id);

        if (null == facture) {
            LOGGER.error("Invalid facture Id: {}", id);
            return "redirect:/factures/list";
        }
        model.addAttribute("facture", facture);
        return "factures/update";
    }

    @PostMapping("/update")
    public String updateFacture(@RequestParam("id") final Long id,
            @Valid @ModelAttribute("facture") Facture facture,
            final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            LOGGER.info("POST request FAILED for: /factures/update/{id}");
            return "/factures/update";
        }
        facture.setId(id);
        factureService.updateFactureById(facture, id);
        model.addAttribute("facture", facture);
        return "redirect:/factures/list";
    }

    @GetMapping("/delete")
    public String deleteFacture(@RequestParam("id") final Long id,
            final Model model) {
        factureService.deleteFactureById(id);
        model.addAttribute("factures", factureRepository.findAll());
        return "redirect:/factures/list";
    }

}
