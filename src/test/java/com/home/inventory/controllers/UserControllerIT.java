package com.home.inventory.controllers;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.home.inventory.entities.User;
import com.home.inventory.repository.UserRepository;
import com.home.inventory.services.interfaces.IUserService;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepository userRepository;

    private static final String URI_USER_LIST = "/user/list";
    private static final String URI_USER_DELETE = "/user/delete?id=";
    private static final String URI_USER_UPDATE = "/user/update?id=9999";
    private static final String URI_USER_UPDATE_FIRST = "/user/update?id=";
    private static final String URI_USER_VALIDATE = "/user/validate";
    private static final String URI_USER_ADD = "/user/add";

    @BeforeEach
    public void setUpPerTest() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        userRepository.deleteAll();
    }

    @Test
    @Tag("/user/list")
    @DisplayName("Get - list")
    public void givenZeroUser_whenGetList_thenreturnOk() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_USER_LIST)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andReturn();
    }

    @Test
    @Tag("/user/add")
    @DisplayName("Get - add")
    public void givenUserPage_whenGetAdd_thenReturnOk() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_USER_ADD)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(
                        MockMvcResultMatchers.model().attributeExists("user"))
                .andReturn();
    }

    @Test
    @Tag("/user/validate")
    @DisplayName("Post - validate - OK")
    public void givenValidUser_whenValidate_thenReturnSaved() throws Exception {

        User user = new User(1l, "username", "user@gmail.com", "Password1&",
                "USER");
        this.mockMvc
                .perform(MockMvcRequestBuilders.post(URI_USER_VALIDATE)
                        .contentType(APPLICATION_JSON)
                        .param("id", user.getId().toString())
                        .param("username", user.getUsername())
                        .param("email", user.getEmail())
                        .param("password", user.getPassword())
                        .param("role", user.getRole()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl(URI_USER_LIST))
                .andReturn();
    }

    @Test
    @Tag("/user/validate")
    @DisplayName("Post - validate - ERROR - Invalid password")
    public void givenInvalidPassword_whenValidate_thenReturnModelErrors()
            throws Exception {

        User user = new User(1l, "username", "user@gmail.com",
                "passwordinvalid", "USER");
        this.mockMvc
                .perform(MockMvcRequestBuilders.post(URI_USER_VALIDATE)
                        .contentType(APPLICATION_JSON)
                        .param("id", user.getId().toString())
                        .param("username", user.getUsername())
                        .param("email", user.getEmail())
                        .param("password", user.getPassword())
                        .param("role", user.getRole()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andReturn();
    }

    @Test
    @Tag("/user/update")
    @DisplayName("Get - Update - OK")
    public void givenOneUser_whenUpdate_thenReturnUpdated() throws Exception {

        User user = new User(null, "username", "user@gmail.com", "Password1&",
                "USER");
        userService.saveUser(user);

        Long id = userRepository.findAll().get(0).getId();

        this.mockMvc.perform(MockMvcRequestBuilders.get(URI_USER_UPDATE_FIRST+id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(
                        MockMvcResultMatchers.model().attributeExists("user"))
                       .andExpect(MockMvcResultMatchers.view().name("user/update"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    @Tag("/user/update")
    @DisplayName("Post - Update - OK")
    public void givenOneUser_whenUpdate_thenReturnUpdate() throws Exception {

        User user = new User(null, "username", "user@gmail.com", "Password1&",
                "USER");
        userService.saveUser(user);
        assertThat(userRepository.findAll().size()).isEqualTo(1);

        Long id = userRepository.findAll().get(0).getId();
        this.mockMvc
                .perform(MockMvcRequestBuilders.post(URI_USER_UPDATE_FIRST+id)
                        .contentType(MediaType.ALL)
                        .param("username", user.getUsername())
                        .param("email", "other@gmail.com")
                        .param("password", "Password2&changed")
                        .param("role", "ADMIN"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl(URI_USER_LIST))
                .andReturn();
    }

    @Test
    @Tag("/user/update")
    @DisplayName("Post - Update - ERROR - Bad id")
    public void givenBadId_whenUpdate_thenReturnErrors() throws Exception {

        User user = new User(1l, "username", "user@gmail.com", "Password1&",
                "USER");
        userService.saveUser(user);

        this.mockMvc.perform(MockMvcRequestBuilders.post(URI_USER_UPDATE))
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andReturn();
    }

    @Test
    @Tag("/user/delete")
    @DisplayName("Delete - OK")
    public void givenUser_whenDelete_thenReturnDeleted() throws Exception {

        User user = new User("username", "user@gmail.com", "Password1&",
                "USER");
        userService.saveUser(user);
        Long id = userRepository.findAll().get(0).getId();

        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URI_USER_DELETE + id)
                        .contentType(MediaType.ALL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.redirectedUrl(URI_USER_LIST))
                .andReturn();

        assertThat(userRepository.findAll().size()).isEqualTo(0);
    }
}
