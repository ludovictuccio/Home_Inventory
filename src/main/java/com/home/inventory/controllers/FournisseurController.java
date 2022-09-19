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

import com.home.inventory.entities.Fournisseur;
import com.home.inventory.repository.FournisseurRepository;
import com.home.inventory.services.interfaces.IFournisseurService;

@Controller
@RequestMapping("/fournisseurs")
public class FournisseurController {

    private static final Logger LOGGER = LogManager
            .getLogger("FournisseurController");

    @Autowired
    private IFournisseurService fournisseurService;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @GetMapping("/list")
    public String home(final Model model) {
        model.addAttribute("fournisseurs", fournisseurRepository.findAll());
        return "fournisseurs/list";
    }

    @GetMapping("/add")
    public String addFournisseur(final Model model) {
        model.addAttribute("fournisseur", new Fournisseur());
        return "fournisseurs/add";
    }

    @PostMapping("/validate")
    public String validate(
            @Valid @ModelAttribute("fournisseur") Fournisseur fournisseur,
            final BindingResult result, final Model model) {
        if (!result.hasErrors()) {
            Fournisseur fournisseurToAdd = fournisseurService
                    .addFournisseur(fournisseur);
            model.addAttribute("fournisseur", fournisseurToAdd);
            return "redirect:/fournisseurs/list";
        }
        LOGGER.info("POST request FAILED for: /fournisseurs/validate");
        return "fournisseurs/add";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id") final Long id,
            final Model model) {
        Fournisseur fournisseur = fournisseurService.getFournisseurById(id);

        if (fournisseur == null) {
            LOGGER.error("Invalid fournisseur Id: {}", id);
            return "redirect:/fournisseurs/list";
        }
        model.addAttribute("fournisseur", fournisseur);
        return "fournisseurs/update";
    }

    @PostMapping("/update")
    public String updateFournisseur(@RequestParam("id") final Long id,
            @Valid @ModelAttribute("fournisseur") Fournisseur fournisseur,
            final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            LOGGER.info("POST request FAILED for: /fournisseurs/update/{id}");
            return "fournisseurs/update";
        }
        fournisseur.setId(id);
        fournisseurService.updateFournisseurById(fournisseur, id);
        model.addAttribute("fournisseur", fournisseur);
        return "redirect:/fournisseurs/list";
    }

    @GetMapping("/delete")
    public String deleteFournisseur(@RequestParam("id") final Long id,
            final Model model) {
        fournisseurService.deleteFournisseurById(id);
        model.addAttribute("fournisseurs", fournisseurRepository.findAll());
        return "redirect:/fournisseurs/list";
    }
}
