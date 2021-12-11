package com.home.inventory.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.inventory.repository.FournisseurRepository;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FournisseurControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String FOUNISSEURS = "fournisseurs";
    private static final String FOUNISSEUR = "fournisseur";

    private static final String URI_GET_FOURNISSEURS_LIST = "/fournisseurs/list";
    private static final String URI_ADD_FOUNISSEURS = "/fournisseurs/add";
    private static final String URI_PUT_FOUNISSEURS = "/fournisseurs/update/5";

    @BeforeEach
    public void setUpPerTest() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        fournisseurRepository.deleteAll();
    }

    @Test
    @Tag("/fournisseurs/list")
    @DisplayName("Get - list")
    public void givenFournisseursList_whenGetList_thenReturnOk()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_GET_FOURNISSEURS_LIST)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute(FOUNISSEURS,
                        fournisseurRepository.findAll()))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("/fournisseurs/add")
    @DisplayName("Get - add")
    public void givenGetMapping_whenAdd_thenReturnOk() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_ADD_FOUNISSEURS)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model()
                        .attributeExists(FOUNISSEUR))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(status().isOk()).andReturn();
    }

//    @Test
//    @Tag("/fournisseurs/update/{id}")
//    @DisplayName("Update by id")
//    public void givenUpdate_whenPostWithValidInfos_thenreturnOk()
//            throws Exception {
//        Fournisseur fournisseur = new Fournisseur(5L, "Casto");
//        String jsonContent = objectMapper.writeValueAsString(fournisseur);
//        fournisseurRepository.save(fournisseur);
//
//        this.mockMvc
//                .perform(MockMvcRequestBuilders.post(URI_PUT_FOUNISSEURS)
//                        .contentType(MediaType.ALL).content(jsonContent))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
//                .andExpect(MockMvcResultMatchers
//                        .redirectedUrl(URI_GET_FOURNISSEURS_LIST))
//                .andReturn();
//    }
}
