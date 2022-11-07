package com.nnk.springboot.controllers.rest;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserRestController {
    @Autowired
    private final UserService service;

    private final String IDNotFoundMessage = "No user found with id:";

    /**
     * Constructor.
     * @param service service layer
     */
    public UserRestController(UserService service) {this.service = service;}

    /**
     * Returns all users.
     */
    @GetMapping
    public List<User> getUsers() {
        return service.getUsers();
    }

    /**
     * Returns a user if it exists.
     * @param id ID of user to find
     * @return a user or throws ResourceNotFoundException
     *
     * @see com.nnk.springboot.domain.User
     * @see com.nnk.springboot.exceptions.ResourceNotFoundException
     */
    @GetMapping("/{id}")
    public User getUserByID(@PathVariable Integer id) {
        Assert.notNull(id, "ID must be provided.");
        return service.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException(IDNotFoundMessage + id +"."));
    }

    /**
     * Creates a user.
     * @param user User to create.
     * @return saved user.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    /**
     * Updates a user.
     * @param user User to update.
     * @return updated user.
     */
    @PutMapping
    public User updateUser(@RequestBody User user) {
        return service.updateUser(user);
    }

    /**
     * Deletes a user.
     *
     * @param id
     *         ID of user to delete.
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer id) {
        if (id == null) throw new ResourceNotFoundException(IDNotFoundMessage + id + ".");
        service.deleteUser(id);
    }
}
