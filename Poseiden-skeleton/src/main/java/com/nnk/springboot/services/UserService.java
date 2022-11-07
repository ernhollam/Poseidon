package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    final
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates new user.
     */
    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    /**
     * Gets a user.
     *
     * @param id
     *         ID of user to find
     *
     * @return Optional user object.
     */
    public Optional<User> getUserById(final Integer id) {
        Assert.notNull(id, "ID must not be null.");
        return userRepository.findById(id);
    }

    /**
     * Returns list of all users.
     */
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Updates a user.
     *
     * @param user
     *         user to update
     *
     * @return updated user object.
     */
    public User updateUser(final User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            return userRepository.save(user);
        } else {
            log.error("Provided user with ID " + user.getId() + "does not exist.");
            throw new ResourceNotFoundException("Provided user with ID " + user.getId() + "does not exist.");
        }
    }

    /**
     * Deletes a user.
     *
     * @param id
     *         ID of user to delete.
     */
    public void deleteUser(final Integer id) {
        Assert.notNull(id, "ID must not be null.");
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            log.debug("Deleted user with ID "+ id +".");
        } else {
            log.error("There is no user with ID " + id + ".");
            throw new ResourceNotFoundException("There is no user with ID " + id + ".");
        }
    }

}
