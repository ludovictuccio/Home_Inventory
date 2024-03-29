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
import com.home.inventory.entities.Facture;
import com.home.inventory.entities.Fournisseur;
import com.home.inventory.entities.Produit;
import com.home.inventory.entities.SousCategories;
import com.home.inventory.repository.CategoriesRepository;
import com.home.inventory.repository.FactureRepository;
import com.home.inventory.repository.FournisseurRepository;
import com.home.inventory.repository.ProduitRepository;
import com.home.inventory.repository.SousCategoriesRepository;
import com.home.inventory.services.interfaces.IProduitService;

import java.time.LocalDate;

@Controller
@RequestMapping("/produits")
public class ProduitController {

    private static final Logger LOGGER = LogManager
            .getLogger("ProduitController");

    @Autowired
    private IProduitService produitService;

    @Autowired
    private ProduitRepository produitRepo;
    @Autowired
    private CategoriesRepository categoriesRepo;
    @Autowired
    private SousCategoriesRepository sousCategoriesRepo;
    @Autowired
    private FournisseurRepository fournisseurRepo;
    @Autowired
    private FactureRepository factureRepo;

    @GetMapping("/list")
    public String home(final Model model) {
        model.addAttribute("produits", produitRepo.findAll());
        return "/produits/list";
    }

    @GetMapping("/add")
    public String addProduit(final Model model) {
        model.addAttribute("categories", categoriesRepo.findAll());
        model.addAttribute("category", new Categories());
        model.addAttribute("sousCategories", sousCategoriesRepo.findAll());
        model.addAttribute("sousCategory", new SousCategories());
        model.addAttribute("fournisseurs", fournisseurRepo.findAll());
        model.addAttribute("fournisseur", new Fournisseur());
        model.addAttribute("factures", factureRepo.findAll());
        model.addAttribute("facture", new Facture());
        model.addAttribute("produit", new Produit());
        return "/produits/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("produit") Produit produit,
                           @Valid @ModelAttribute("category")  Categories category,
                           @Valid @ModelAttribute("sousCategory")  SousCategories sousCategory,
                           @Valid @ModelAttribute("fournisseur")  Fournisseur fournisseur,
                           @Valid @ModelAttribute("facture")  Facture facture,
            final BindingResult result, final Model model) {
        if (!result.hasErrors()) {
            produit.setCategorieProduit(category);
            produit.setSousCategorieProduit(sousCategory);
            produit.setFournisseurProduit(fournisseur);
            produit.setFactureProduit(facture);
            Produit produitToAdd = produitService.addProduit(produit);
            model.addAttribute("produit", produitToAdd);
            return "redirect:/produits/list";
        }
        LOGGER.info("POST request FAILED for: /produits/validate");
        return "/produits/add";
    }

    @GetMapping("/get")
    public String showGetForm(@RequestParam("id") final Long id,
            final Model model) {
        Produit produit = produitService.getProduitById(id);

        if (produit == null) {
            LOGGER.error("Invalid produit Id: {}", id);
            return "redirect:/produits/list";
        }
        model.addAttribute("produit", produit);
        return "/produits/get";
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
        model.addAttribute("category", produit.getCategorieProduit());
        model.addAttribute("sousCategory", produit.getSousCategorieProduit());
        model.addAttribute("fournisseur", produit.getFournisseurProduit());
        model.addAttribute("facture", produit.getFactureProduit());

        model.addAttribute("categories", categoriesRepo.findAll());
        model.addAttribute("sousCategories", sousCategoriesRepo.findAll());
        model.addAttribute("fournisseurs", fournisseurRepo.findAll());
        model.addAttribute("factures", factureRepo.findAll());
        return "/produits/update";
    }

    @PostMapping("/update")
    public String updateProduit(@RequestParam("id") final Long id,
            @Valid @ModelAttribute("produit") Produit produit,
                                @Valid @ModelAttribute("category")  Categories category,
                                @Valid @ModelAttribute("sousCategory")  SousCategories sousCategory,
                                @Valid @ModelAttribute("fournisseur")  Fournisseur fournisseur,
                                @Valid @ModelAttribute("facture")  Facture facture,
            final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            LOGGER.info("POST request FAILED for: /produits/update/{id}");
            return "/produits/update";
        }
        Produit produitToUpdate = new Produit();
        produitToUpdate.setId(id);

        // mapper les 2 produits pour recuperer les autres attributs
        produitToUpdate.setDescriptionProduit("test");

        produitToUpdate.setCategorieProduit(category);
        produitToUpdate.setSousCategorieProduit(sousCategory);
        produitToUpdate.setFournisseurProduit(fournisseur);
        produitToUpdate.setFactureProduit(facture);

//        Produit produitUpdated = produitService.updateProduitById(produit, id);
//        model.addAttribute("produit", produitUpdated);
        produitService.updateProduitById(produitToUpdate);
        model.addAttribute("produit", produit);
        return "redirect:/produits/get?id=" + id;
    }

    @GetMapping("/delete")
    public String deleteProduit(@RequestParam("id") final Long id,
            final Model model) {
        produitService.deleteProduitById(id);
        model.addAttribute("produits", produitRepo.findAll());
        return "redirect:/produits/list";
    }

}
