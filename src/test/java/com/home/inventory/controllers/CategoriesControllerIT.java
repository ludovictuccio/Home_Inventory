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
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.inventory.entities.Categories;
import com.home.inventory.repository.CategoriesRepository;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CategoriesControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String CATEGORIES = "categories";
    private static final String CATEGORY = "category";

    private static final String URI_GET_CATEGORIES_LIST = "/categories/list";
    private static final String URI_ADD_CATEGORIES = "/categories/add";
    private static final String URI_PUT_CATEGORIES = "/categories/update/5";

    @BeforeEach
    public void setUpPerTest() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        categoriesRepository.deleteAll();
    }

    @Test
    @Tag("/categories/list")
    @DisplayName("Get - list")
    public void givenCategoriesList_whenGetList_thenReturnOk()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_GET_CATEGORIES_LIST)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute(CATEGORIES,
                        categoriesRepository.findAll()))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("/categories/add")
    @DisplayName("Get - add")
    public void givenGetMapping_whenAdd_thenReturnOk() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_ADD_CATEGORIES)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.model().attributeExists(CATEGORY))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("/categories/update/{id}")
    @DisplayName("Post - update")
    public void givenUpdate_whenPostWithValidInfos_thenreturnOk()
            throws Exception {
        Categories category = new Categories(5L, "Maison");
        String jsonContent = objectMapper.writeValueAsString(category);
        categoriesRepository.save(category);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(URI_PUT_CATEGORIES)
                        .contentType(MediaType.ALL).content(jsonContent))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers
                        .redirectedUrl(URI_GET_CATEGORIES_LIST))
                .andReturn();
    }

}
