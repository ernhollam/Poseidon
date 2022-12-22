package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = LoginController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @Test
    @WithMockUser
    public void testGetAllUserArticles() throws Exception {
        List<User> expectedList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(expectedList);
        mockMvc.perform(get("/app/secure/article-details"))
               .andExpect(view().name("user/list"))
               .andExpect(model().attribute("users", expectedList));
    }

    @Test
    @WithMockUser
    public void testError() throws Exception {
        mockMvc.perform(get("/app/error"))
               .andExpect(view().name("../static/error/403"))
               .andExpect(model().attributeExists("errorMsg"))
               .andExpect(model().attribute("errorMsg", "You are not authorized for the requested data."));
    }
}