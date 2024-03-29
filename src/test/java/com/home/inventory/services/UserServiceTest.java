package com.home.inventory.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import com.home.inventory.entities.User;
import com.home.inventory.repository.UserRepository;
import com.home.inventory.services.interfaces.IUserService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserServiceTest {

    @Autowired
    public IUserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static String encryptedPassword = "encrypted_password";

    private User user;
    private User userTwo;
    private User result;

    private List<User> allUsers;

    @BeforeEach
    public void setUpPerTest() {
        allUsers = new ArrayList<>();

        user = new User("username1", "user1@gmail.com", "validPassword1&",
                "user");
        user.setId(1l);
        allUsers.add(user);

        userTwo = new User("username2", "user2@gmail.com", "validPassword2&",
                "user");
        userTwo.setId(2l);
        allUsers.add(userTwo);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - OK")
    public void givenValidUser_whenSave_thenReturnSaved() {
        // GIVEN
        when(bCryptPasswordEncoder.encode(user.getPassword()))
                .thenReturn(encryptedPassword);

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result.getUsername()).isEqualTo("username1");
        assertThat(result.getPassword()).isEqualTo(encryptedPassword);
        assertThat(result.getEmail()).isEqualTo("user1@gmail.com");
        assertThat(result.getRole()).isEqualTo("USER");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Empty username")
    public void givenEmptyUsername_whenSave_thenReturnNull() {
        // GIVEN
        user.setUsername("");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Username already used in DB")
    public void givenAlreadyExistingUsername_whenSave_thenReturnNull() {
        // GIVEN
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findAll()).thenReturn(allUsers);
        when(userRepository.findUserByUsername(user.getUsername()))
                .thenReturn(user);

        User alreadyExistingUsername = new User("username1", "user1@gmail.com",
                "validPassword1&", "user");
        alreadyExistingUsername.setId(80l);

        // WHEN
        result = userService.saveUser(alreadyExistingUsername);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Empty password")
    public void givenEmptyPassword_whenSave_thenReturnNull() {
        // GIVEN
        user.setPassword("");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Empty email")
    public void givenEmptyEmail_whenSave_thenReturnNull() {
        // GIVEN
        user.setEmail("");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Empty role")
    public void givenEmptyRole_whenSave_thenReturnNull() {
        // GIVEN
        user.setRole("");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Password without uppercase letter")
    public void givenPasswordWithoutUppercaseLetter_whenSave_thenReturnNull() {
        // GIVEN
        user.setPassword("password1&");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Password without lowercase letter")
    public void givenPasswordWithoutLowercaseLetter_whenSave_thenReturnNull() {
        // GIVEN
        user.setPassword("PASSWORD1&");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Password without special character")
    public void givenPasswordWithoutSpecialCharacter_whenSave_thenReturnNull() {
        // GIVEN
        user.setPassword("Password1");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Password size < 8")
    public void givenPasswordWithSizeLessThanMini_whenSave_thenReturnNull() {
        // GIVEN
        user.setPassword("Pass1*");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Password without digit")
    public void givenPasswordWithoutDigits_whenSave_thenReturnNull() {
        // GIVEN
        user.setPassword("Password*");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("FIND")
    @DisplayName("Find all - OK - 2 users")
    public void givenTwoUsers_whenFindAll_thenReturnTwoSizeList() {
        // GIVEN
        when(userService.findAllUsers()).thenReturn(allUsers);

        // WHEN
        List<User> resultList = userService.findAllUsers();

        // THEN
        verify(userRepository, times(1)).findAll();
        assertThat(resultList).isNotNull();
        assertThat(resultList.size()).isEqualTo(2);
        assertThat(userRepository.findAll().size()).isEqualTo(2);
        assertThat(userRepository.findAll().get(0).getId()).isNotNull();
        assertThat(userRepository.findAll().get(0).getUsername())
                .isEqualTo("username1");
        assertThat(userRepository.findAll().get(1).getId()).isNotNull();
        assertThat(userRepository.findAll().get(1).getUsername())
                .isEqualTo("username2");
    }

    @Test
    @Tag("FIND")
    @DisplayName("Find all - Ok - Empty list / size = 0")
    public void givenZeroUser_whenFindAll_thenReturnEmptyList() {
        // GIVEN
        allUsers.clear();
        when(userService.findAllUsers()).thenReturn(allUsers);

        // WHEN
        List<User> resultList = userService.findAllUsers();

        // THEN
        verify(userRepository, times(1)).findAll();
        assertThat(resultList).isNotNull();
        assertThat(resultList.size()).isEqualTo(0);
        assertThat(userRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - OK - Existing username")
    public void givenValidUsername_whenUpdate_thenReturnTrue() {
        // GIVEN
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.save(userTwo)).thenReturn(userTwo);
        when(userRepository.findUserByUsername("username1")).thenReturn(user);
        when(userService.findAllUsers()).thenReturn(allUsers);

        User existingUsernameForUpdate = new User("username1",
                "updated@gmail.com", "validPassword1&updated", "user");

        when(bCryptPasswordEncoder
                .encode(existingUsernameForUpdate.getPassword()))
                        .thenReturn(encryptedPassword);

        // WHEN
        boolean result = userService.updateUser(existingUsernameForUpdate);

        // THEN
        assertThat(result).isTrue();
        verify(userRepository, times(1)).save(user);
        assertThat(userRepository.findUserByUsername("username1").getPassword())
                .isEqualTo(encryptedPassword);
        assertThat(userRepository.findUserByUsername("username1").getEmail())
                .isEqualTo("updated@gmail.com");
        assertThat(userRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - OK - Role changed")
    public void givenValidUsername_whenUpdateRole_thenReturnTrue() {
        // GIVEN
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.save(userTwo)).thenReturn(userTwo);
        when(userRepository.findUserByUsername("username1")).thenReturn(user);
        when(userService.findAllUsers()).thenReturn(allUsers);

        User existingUsernameForUpdate = new User("username1",
                "updated@gmail.com", "validPassword1&updated", "admin");

        when(bCryptPasswordEncoder
                .encode(existingUsernameForUpdate.getPassword()))
                        .thenReturn(encryptedPassword);

        // WHEN
        boolean result = userService.updateUser(existingUsernameForUpdate);

        // THEN
        assertThat(result).isTrue();
        verify(userRepository, times(1)).save(user);
        assertThat(userRepository.findUserByUsername("username1").getPassword())
                .isEqualTo(encryptedPassword);
        assertThat(userRepository.findUserByUsername("username1").getEmail())
                .isEqualTo("updated@gmail.com");
        assertThat(userRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Invalid username")
    public void givenInvalidUsername_whenUpdateRole_thenReturnFalse() {
        // GIVEN
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.save(userTwo)).thenReturn(userTwo);
        when(userRepository.findUserByUsername("username1")).thenReturn(user);
        when(userService.findAllUsers()).thenReturn(allUsers);

        User existingUsernameForUpdate = new User("usernameOther",
                "updated@gmail.com", "validPassword1&updated", "admin");

        // WHEN
        boolean result = userService.updateUser(existingUsernameForUpdate);

        // THEN
        assertThat(result).isFalse();
        verify(userRepository, times(0)).save(user);
        assertThat(userRepository.findUserByUsername("username1").getPassword())
                .isEqualTo("validPassword1&");
        assertThat(userRepository.findUserByUsername("username1").getEmail())
                .isEqualTo("user1@gmail.com");
        assertThat(userRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Invalid password")
    public void givenInvalidPassword_whenUpdateRole_thenReturnFalse() {
        // GIVEN
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.save(userTwo)).thenReturn(userTwo);
        when(userRepository.findUserByUsername("username1")).thenReturn(user);
        when(userService.findAllUsers()).thenReturn(allUsers);

        User existingUsernameForUpdate = new User("username1",
                "updated@gmail.com", "invalidPassword", "user");

        // WHEN
        boolean result = userService.updateUser(existingUsernameForUpdate);

        // THEN
        assertThat(result).isFalse();
        verify(userRepository, times(0)).save(user);
        assertThat(userRepository.findUserByUsername("username1").getPassword())
                .isEqualTo("validPassword1&");
        assertThat(userRepository.findUserByUsername("username1").getEmail())
                .isEqualTo("user1@gmail.com");
        assertThat(userRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Invalid role")
    public void givenInvalidRole_whenUpdateRole_thenReturnFalse() {
        // GIVEN
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.save(userTwo)).thenReturn(userTwo);
        when(userRepository.findUserByUsername("username1")).thenReturn(user);
        when(userService.findAllUsers()).thenReturn(allUsers);

        User existingUsernameForUpdate = new User("username1",
                "updated@gmail.com", "validPassword1&", "other");

        // WHEN
        boolean result = userService.updateUser(existingUsernameForUpdate);

        // THEN
        assertThat(result).isFalse();
        verify(userRepository, times(0)).save(user);
        assertThat(userRepository.findUserByUsername("username1").getPassword())
                .isEqualTo("validPassword1&");
        assertThat(userRepository.findUserByUsername("username1").getEmail())
                .isEqualTo("user1@gmail.com");
        assertThat(userRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Empty username")
    public void givenEmptyUsername_whenUpdateRole_thenReturnFalse() {
        // GIVEN
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.save(userTwo)).thenReturn(userTwo);
        when(userRepository.findUserByUsername("username1")).thenReturn(user);
        when(userService.findAllUsers()).thenReturn(allUsers);

        User existingUsernameForUpdate = new User("", "validPassword1&",
                "updated@gmail.com", "other");

        // WHEN
        boolean result = userService.updateUser(existingUsernameForUpdate);

        // THEN
        assertThat(result).isFalse();
        verify(userRepository, times(0)).save(user);
        assertThat(userRepository.findUserByUsername("username1").getPassword())
                .isEqualTo("validPassword1&");
        assertThat(userRepository.findUserByUsername("username1").getEmail())
                .isEqualTo("user1@gmail.com");
        assertThat(userRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Empty password")
    public void givenEmptyPassword_whenUpdateRole_thenReturnFalse() {
        // GIVEN
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.save(userTwo)).thenReturn(userTwo);
        when(userRepository.findUserByUsername("username1")).thenReturn(user);
        when(userService.findAllUsers()).thenReturn(allUsers);

        User existingUsernameForUpdate = new User("username1", "",
                "validPassword1&", "other");

        // WHEN
        boolean result = userService.updateUser(existingUsernameForUpdate);

        // THEN
        assertThat(result).isFalse();
        verify(userRepository, times(0)).save(user);
        assertThat(userRepository.findUserByUsername("username1").getPassword())
                .isEqualTo("validPassword1&");
        assertThat(userRepository.findUserByUsername("username1").getEmail())
                .isEqualTo("user1@gmail.com");
        assertThat(userRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - OK - Valid username")
    public void givenValidUsername_whenDelete_thenReturnTrue() {
        // GIVEN
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findUserByUsername("username1")).thenReturn(user);
        when(userService.findAllUsers()).thenReturn(allUsers);

        // WHEN
        boolean isDeleted = userService.deleteUser("username1");

        // THEN
        assertThat(isDeleted).isTrue();
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - ERROR - Invalid username")
    public void givenInvalidUsername_whenDelete_thenReturnFalse() {
        // GIVEN
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findUserByUsername("unknow")).thenReturn(null);
        when(userService.findAllUsers()).thenReturn(allUsers);

        // WHEN
        boolean isDeleted = userService.deleteUser("unknow");

        // THEN
        assertThat(isDeleted).isFalse();
        verify(userRepository, times(0)).delete(user);
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - ERROR - Empty username")
    public void givenEmptyUsername_whenDelete_thenReturnFalse() {
        // GIVEN
        when(userRepository.save(user)).thenReturn(user);

        // WHEN
        boolean isDeleted = userService.deleteUser("");

        // THEN
        assertThat(isDeleted).isFalse();
        verify(userRepository, times(0)).delete(user);
    }

}
