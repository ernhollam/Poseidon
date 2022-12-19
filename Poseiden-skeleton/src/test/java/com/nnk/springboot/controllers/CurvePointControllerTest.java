package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CurveController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CurvePointControllerTest {
    @Autowired
    MockMvc           mockMvc;
    @MockBean
    CurvePointService service;

    private CurvePoint       curvePoint;
    private List<CurvePoint> curvePointList;

    @BeforeAll
    public void setUp() {
        curvePoint = new CurvePoint(3, 5d, 10d);
        curvePointList = Collections.singletonList(curvePoint);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Return list of curve points")
    public void homeTest() throws Exception {
        when(service.getCurvePoints()).thenReturn(curvePointList);

        mockMvc.perform(get("/curvePoint/list"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(model().attributeExists("curvePoints"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Show add curve point form")
    public void addCurvePointFormTest() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Add new curve point successful")
    public void validateTest() throws Exception {
        when(service.saveCurvePoint(curvePoint)).thenReturn(curvePoint);

        mockMvc.perform(post("/curvePoint/validate")
                                .with(csrf().asHeader())
                                .param("curveId", "3")
                                .param("term", "5")
                                .param("value", "10"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("success"))
               .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Show update form successful")
    public void showUpdateFormIsSuccessful() throws Exception {
        when(service.getCurvePointById(1)).thenReturn(Optional.of(curvePoint));
        when(service.getCurvePoints()).thenReturn(curvePointList);

        mockMvc.perform(get("/curvePoint/update/{id}", "1"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(model().attributeExists("curvePoint"))
               .andExpect(view().name("curvePoint/update"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Show update form failed")
    public void showUpdateFormFails() throws Exception {
        when(service.getCurvePointById(3)).thenReturn(Optional.empty());
        when(service.getCurvePoints()).thenReturn(curvePointList);

        mockMvc.perform(get("/curvePoint/update/{id}", "3"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("error"))
               .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Update curve point successful")
    public void updateCurvePointTest() throws Exception {
        when(service.getCurvePointById(any(Integer.class))).thenReturn(Optional.of(curvePoint));

        mockMvc.perform(post("/curvePoint/update/{id}", "1")
                                .with(csrf().asHeader())
                                .param("curveId", "3")
                                .param("term", "5")
                                .param("value", "10"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("success"))
               .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete curve point successful")
    public void deleteCurvePointIsSuccessful() throws Exception {
        when(service.getCurvePointById(any(Integer.class))).thenReturn(Optional.of(curvePoint));
        //when(service.getCurvePoints()).thenReturn(curvePointList);

        mockMvc.perform(get("/curvePoint/delete/{id}", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("success"))
               .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete curve point failed")
    public void deleteCurvePointFailed() throws Exception {
        when(service.getCurvePointById(any(Integer.class))).thenReturn(Optional.empty());
        //when(service.getCurvePoints()).thenReturn(curvePointList);

        mockMvc.perform(get("/curvePoint/delete/{id}", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("error"))
               .andExpect(view().name("redirect:/curvePoint/list"));
    }
}
