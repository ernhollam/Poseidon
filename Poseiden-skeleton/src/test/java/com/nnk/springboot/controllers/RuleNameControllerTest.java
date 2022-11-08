package com.nnk.springboot.controllers;

import com.nnk.springboot.controllers.thymeleaf.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@WebMvcTest(RuleNameController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RuleNameControllerTest {
    @Autowired
    MockMvc     mockMvc;
    @MockBean
    RuleService service;

    private RuleName       ruleName;
    private List<RuleName> ruleNames;

    @BeforeAll
    public void setUp() {
        ruleName = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");
        ruleNames = Collections.singletonList(ruleName);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Return list of rules")
    public void homeTest() throws Exception {
        when(service.getRules()).thenReturn(ruleNames);

        mockMvc.perform(get("/ruleName/list"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(model().attributeExists("rules"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Show add rule form")
    public void addRatingFormTest() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Add new rule successful")
    public void validateTest() throws Exception {
        when(service.saveRule(ruleName)).thenReturn(ruleName);

        mockMvc.perform(post("/ruleName/validate")
                                .param("curveId", "3")
                                .param("term", "5")
                                .param("value", "10"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("success"))
               .andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Show update form successful")
    public void showUpdateFormIsSuccessful() throws Exception {
        when(service.getRuleById(3)).thenReturn(Optional.of(ruleName));
        when(service.getRules()).thenReturn(ruleNames);

        mockMvc.perform(get("/ruleName/update/{id}", "3"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(model().attributeExists("ruleName"))
               .andExpect(view().name("ruleName/update"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Show update form failed")
    public void showUpdateFormFails() throws Exception {
        when(service.getRuleById(3)).thenReturn(Optional.empty());
        when(service.getRules()).thenReturn(ruleNames);

        mockMvc.perform(get("/ruleName/update/{id}", "3"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("error"))
               .andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Update rule successful")
    public void updateRuleNameTest() throws Exception {
        when(service.getRuleById(any(Integer.class))).thenReturn(Optional.of(ruleName));

        mockMvc.perform(post("/ruleName/update/{id}", "1")
                                .param("name", "name")
                                .param("description", "description")
                                .param("json", "json")
                                .param("template", "template")
                                .param("sqlStr", "sqlStr")
                                .param("sqlPart", "sqlPart"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("success"))
               .andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete rating successful")
    public void deleteRatingIsSuccessful() throws Exception {
        when(service.getRuleById(any(Integer.class))).thenReturn(Optional.of(ruleName));
        //when(service.getRatings()).thenReturn(ratingList);

        mockMvc.perform(get("/ruleName/delete/{id}", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("success"))
               .andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete rating failed")
    public void deleteRatingFailed() throws Exception {
        when(service.getRuleById(any(Integer.class))).thenReturn(Optional.empty());
        //when(service.getRatings()).thenReturn(ratingList);

        mockMvc.perform(get("/ruleName/delete/{id}", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("error"))
               .andExpect(view().name("redirect:/ruleName/list"));
    }
}
