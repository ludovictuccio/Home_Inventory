package com.home.inventory.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.home.inventory.entities.User;
import com.home.inventory.repository.UserRepository;
import com.home.inventory.services.interfaces.IUserService;
import com.home.inventory.util.ConstraintsValidator;

@Service
public class UserService implements IUserService {

    private static final Logger LOGGER = LogManager.getLogger("UserService");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * {@inheritDoc}
     */
    public User saveUser(final User user) {

        if (ConstraintsValidator.checkValidUser(user) == null) {
            LOGGER.error("Save user process exit.");
            return null;
        } else if ((userRepository
                .findUserByUsername(user.getUsername()) != null)
                || (userRepository.findUserByEmail(user.getEmail()) != null)) {
            LOGGER.error(
                    "ERROR: this username or email is already used. Please change.");
            return null;
        }
        user.setRole(user.getRole().toUpperCase());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    /**
     * {@inheritDoc}
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    public boolean updateUser(final User user) {
        boolean isUpdated = false;

        if (ConstraintsValidator.checkValidUser(user) == null) {
            return isUpdated;
        }
        User existingUser = userRepository
                .findUserByUsername(user.getUsername());

        if (existingUser == null) {
            LOGGER.error("Unknow user for username: {}", user.getUsername());
            return isUpdated;
        }
        existingUser
                .setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        existingUser.setEmail(user.getEmail());
        existingUser.setUsername(user.getUsername());
        existingUser.setRole(user.getRole().toUpperCase());
        userRepository.save(existingUser);
        isUpdated = true;
        return isUpdated;
    }

    /**
     * {@inheritDoc}
     */
    public boolean deleteUser(final String username) {
        boolean isDeleted = false;

        User existingUser = userRepository.findUserByUsername(username);

        if (existingUser == null) {
            LOGGER.error("Unknow user for username: {}", username);
            return isDeleted;
        }
        userRepository.delete(existingUser);
        isDeleted = true;
        return isDeleted;
    }
}
