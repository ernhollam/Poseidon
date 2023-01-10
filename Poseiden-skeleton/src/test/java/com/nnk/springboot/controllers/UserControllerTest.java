package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(value = IllegalArgumentException.class)
public class UserControllerTest {
    @Autowired
    MockMvc               mockMvc;
    @MockBean
    UserService           userService;
    @MockBean
    BCryptPasswordEncoder encoder;

    private User       user;
    private List<User> users;
    private String encodedPassword;

    @BeforeAll
    public void setUp() {
        user = new User();
        users = Collections.singletonList(user);
        encodedPassword = "-*/PASSw0rd!";
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Return list of users")
    public void homeTest() throws Exception {
        when(userService.getUsers()).thenReturn(users);

        mockMvc.perform(get("/user/list"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(model().attributeExists("users"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Show add user form")
    public void addUserFormTest() throws Exception {
        mockMvc.perform(get("/user/add"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Add new user successful")
    public void validateTest() throws Exception {
        when(encoder.encode(anyString())).thenReturn(encodedPassword);
        when(userService.saveUser(user)).thenReturn(user);

        mockMvc.perform(post("/user/validate")
                                .with(csrf().asHeader())
                                .param("fullname", "My Full Name")
                                .param("username", "username")
                                .param("password", encodedPassword)
                                .param("role", "ADMIN"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Add new user with invalid password")
    public void validateTest_withInvalidPassword() throws Exception {
        String invalidPassword = "abc123";
        when(encoder.encode(anyString())).thenReturn(invalidPassword);
        when(userService.saveUser(user)).thenReturn(user);

        mockMvc.perform(post("/user/validate")
                                .with(csrf().asHeader())
                                .param("fullname", "My Full Name")
                                .param("username", "username")
                                .param("password", invalidPassword)
                                .param("role", "ADMIN"))
               .andExpect(model().hasErrors())
               .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Show update form successful")
    public void showUpdateFormIsSuccessful() throws Exception {
        when(userService.getUserById(any(Integer.class))).thenReturn(Optional.of(user));


        mockMvc.perform(get("/user/update/{id}", "1"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(model().attributeExists("user"))
               .andExpect(view().name("user/update"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Show update form failed")
    public void showUpdateFormFails() {
        when(userService.getUserById(3)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> mockMvc.perform(get("/user/update/{id}", "3")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Update user successful")
    public void updateBidTest() throws Exception {
        when(encoder.encode(anyString())).thenReturn("password");
        when(userService.saveUser(user)).thenReturn(user);
        when(userService.getUsers()).thenReturn(users);

        mockMvc.perform(post("/user/update/{id}", "1")
                                .with(csrf().asHeader())
                                .param("fullname", "My New Full Name")
                                .param("username", "username")
                                .param("password", encodedPassword)
                                .param("role", "ADMIN"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete user successful")
    public void deleteBidIsSuccessful() throws Exception {
        when(userService.getUserById(any(Integer.class))).thenReturn(Optional.of(user));
        when(userService.getUsers()).thenReturn(users);

        mockMvc.perform(get("/user/delete/{id}", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete user failed")
    public void deleteBidFailed() {
        when(userService.getUserById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> mockMvc.perform(get("/user/delete/{id}", "1")));
    }
}
