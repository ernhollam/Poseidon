package com.nnk.springboot.controllers;

import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HomeController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HomeControllerTest {
    @Autowired
    MockMvc mockMvc;


    @Test
    @WithMockUser
    public void testHome() throws Exception {
        mockMvc.perform(get("/"))
               .andExpect(view().name("home"))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAdminHome() throws Exception {
        mockMvc.perform(get("/admin/home"))
               .andExpect(status().is3xxRedirection());
    }
}