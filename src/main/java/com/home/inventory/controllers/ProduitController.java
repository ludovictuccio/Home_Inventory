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

import com.home.inventory.entities.Produit;
import com.home.inventory.repository.ProduitRepository;
import com.home.inventory.services.IProduitService;

@Controller
@RequestMapping("/produits")
public class ProduitController {

    private static final Logger LOGGER = LogManager
            .getLogger("ProduitController");

    @Autowired
    private IProduitService produitService;

    @Autowired
    private ProduitRepository produitRepo;

    @GetMapping("/list")
    public String home(final Model model) {
        model.addAttribute("produits", produitRepo.findAll());
        return "/produits/list";
    }

    @GetMapping("/add")
    public String addProduit(final Model model) {
        model.addAttribute("produit", new Produit());
        return "produits/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("produit") Produit produit,
            final BindingResult result, final Model model) {
        if (!result.hasErrors()) {
            Produit produitToAdd = produitService.addProduit(produit);
            model.addAttribute("produit", produitToAdd);
            return "redirect:/produits/list";
        }
        LOGGER.info("POST request FAILED for: /produits/validate");
        return "/produits/add";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id") final Long id,
            final Model model) {
        Produit produit = produitService.getProduitById(id);

        if (produit == null) {
            LOGGER.error("Invalid produit Id: {}", id);
            return "redirect:/produits/list";
        }
        model.addAttribute("produit", produit);
        return "/produits/update";
    }

    @PostMapping("/update")
    public String updateProduit(@RequestParam("id") final Long id,
            @Valid @ModelAttribute("produit") Produit produit,
            final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            LOGGER.info("POST request FAILED for: /produits/update/{id}");
            return "/produits/update";
        }
        produit.setId(id);
        produitService.updateProduitById(produit, id);
        model.addAttribute("produit", produit);
        return "redirect:/produits/list";
    }

    @GetMapping("/delete")
    public String deleteProduit(@RequestParam("id") final Long id,
            final Model model) {
        produitService.deleteProduitById(id);
        model.addAttribute("produits", produitRepo.findAll());
        return "redirect:/produits/list";
    }

}