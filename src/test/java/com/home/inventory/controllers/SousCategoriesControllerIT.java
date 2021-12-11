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
import com.home.inventory.repository.SousCategoriesRepository;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SousCategoriesControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private SousCategoriesRepository sousCategoriesRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String SOUS_CATEGORIES = "souscategories";
    private static final String SOUS_CATEGORY = "souscategory";

    private static final String URI_GET_SOUSCATEGORIES_LIST = "/souscategories/list";
    private static final String URI_ADD_SOUSCATEGORIES = "/souscategories/add";
    private static final String URI_PUT_SOUSCATEGORIES = "/souscategories/update/1";

    @BeforeEach
    public void setUpPerTest() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        sousCategoriesRepository.deleteAll();
    }

    @Test
    @Tag("/souscategories/list")
    @DisplayName("Get - list")
    public void givenSousCategoriesList_whenGetList_thenReturnOk()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_GET_SOUSCATEGORIES_LIST)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute(
                        SOUS_CATEGORIES, sousCategoriesRepository.findAll()))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("/souscategories/add")
    @DisplayName("Get - add")
    public void givenGetMapping_whenAdd_thenReturnOk() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_ADD_SOUSCATEGORIES)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model()
                        .attributeExists(SOUS_CATEGORY))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(status().isOk()).andReturn();
    }

//    @Test
//    @Tag("/souscategories/update/{id}")
//    @DisplayName("Post - update")
//    public void givenUpdate_whenPostWithValidInfos_thenreturnOk()
//            throws Exception {
//        SousCategories sousCategory = new SousCategories(1L, "Jardin");
//        String jsonContent = objectMapper.writeValueAsString(sousCategory);
//        sousCategoriesRepository.save(sousCategory);
//
//        this.mockMvc
//                .perform(MockMvcRequestBuilders.post(URI_PUT_SOUSCATEGORIES)
//                        .contentType(MediaType.ALL).content(jsonContent))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
//                .andExpect(MockMvcResultMatchers
//                        .redirectedUrl(URI_GET_SOUSCATEGORIES_LIST))
//                .andReturn();
//    }

}
