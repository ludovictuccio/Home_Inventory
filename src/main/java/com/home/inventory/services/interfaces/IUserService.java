package com.home.inventory.services.interfaces;

import java.util.List;

import com.home.inventory.entities.User;

public interface IUserService {

    /**
     * Method service used to save a new user. Username and email are unique
     * attribute and role must be ADMIN or USER.
     *
     * @param user
     * @return user
     */
    User saveUser(User user);

    /**
     * Method service used to find all User.
     *
     * @return all users
     */
    List<User> findAllUsers();

    /**
     * Method service used to update an user.
     *
     * @param user
     * @return isUpdated boolean
     */
    boolean updateUser(User user);

    /**
     * Method service used to delete an user with his username.
     *
     * @param username
     * @return isDeleted boolean
     */
    boolean deleteUser(String username);
}
