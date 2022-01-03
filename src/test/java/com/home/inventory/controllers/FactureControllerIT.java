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
import com.home.inventory.repository.FactureRepository;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FactureControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String FACTURES = "factures";
    private static final String FACTURE = "facture";

    private static final String URI_GET_FACTURES_LIST = "/factures/list";
    private static final String URI_ADD_FACTURE = "/factures/add";
    private static final String URI_PUT_FACTURES = "/factures/update?";

    @BeforeEach
    public void setUpPerTest() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        factureRepository.deleteAll();
    }

    @Test
    @Tag("/factures/list")
    @DisplayName("Get - list")
    public void givenFactureList_whenGetList_thenReturnOk() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_GET_FACTURES_LIST)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute(FACTURES,
                        factureRepository.findAll()))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("/factures/add")
    @DisplayName("Get - add")
    public void givenGetMapping_whenAdd_thenReturnOk() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_ADD_FACTURE)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.model().attributeExists(FACTURE))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(status().isOk()).andReturn();
    }

//    @Test
//    @Tag("/factures/update?id={id}")
//    @DisplayName("Post - update")
//    public void givenUpdate_whenPostWithValidInfos_thenReturnOk()
//            throws Exception {
//        Facture facture = new Facture("A0001");
//        factureRepository.save(facture);
//        assertThat(factureRepository.findAll().size()).isEqualTo(1);
//        assertThat(factureRepository.findAll().get(0).getDescription())
//                .isEqualTo("A0001");
//
//        Long idFacture = factureRepository.findAll().get(0).getId();
//
//        Facture factureToUpdate = new Facture("A0009");
//        String jsonContent = objectMapper.writeValueAsString(factureToUpdate);
//
//        this.mockMvc
//                .perform(MockMvcRequestBuilders.post(URI_PUT_FACTURES)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .param("id", idFacture.toString()).content(jsonContent))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
//                .andExpect(MockMvcResultMatchers.model()
//                        .attributeExists("facture"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers
//                        .redirectedUrl(URI_GET_FACTURES_LIST))
//                .andReturn();
//
//        assertThat(factureRepository.findAll().get(0).getDescription())
//                .isEqualTo("A0009");
//    }

}
