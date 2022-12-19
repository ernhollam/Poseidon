package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(UserService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    /**
     * Class under test
     */
    @Autowired
    UserService    service;
    @MockBean
    UserRepository repository;
    
    private User user;
    private Integer userID;
    @BeforeAll
    void init() {
        userID = 3;
        String encodedPassword = "-*/PASSw0rd!";
        user = new User(userID, "username", encodedPassword, "John Doe", "ADMIN");
    }


    @Test
    public void testSaveUser() {
        when(repository.save(user)).thenReturn(user);
        service.saveUser(user);
        verify(repository, times(1)).save(user);
    }


    @Test
    public void testGetUserById_successful() {
        when(repository.findById(userID)).thenReturn(Optional.of(user));
        assertEquals(Optional.of(user), service.getUserById(userID));
    }

    @Test
    public void testGetUserById_withNullId() {
        assertThrows(Exception.class, () -> service.getUserById(null));
    }

    @Test
    public void testGetUsers() {
        when(repository.findAll()).thenReturn(Collections.singletonList(user));
        assertEquals(Collections.singletonList(user), service.getUsers());
    }

    @Test
    public void testUpdateUser_successful() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        service.updateUser(user);
        verify(repository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser_witIdNotPresent() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.updateUser(user));
    }

    @Test
    public void testDeleteUser_successful() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        service.deleteUser(userID);
        verify(repository, times(1)).deleteById(userID);
    }

    @Test
    public void testDeleteUser_witIdNotPresent() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deleteUser(userID));
    }

    @Test
    public void testDeleteUser_withNullId() {
        assertThrows(Exception.class, () -> service.deleteUser(null));
    }
}