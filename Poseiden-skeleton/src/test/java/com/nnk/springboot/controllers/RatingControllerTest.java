package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
@WebMvcTest(RatingController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RatingControllerTest {
    @Autowired
    MockMvc       mockMvc;
    @MockBean
    RatingService service;

    private Rating       rating;
    private List<Rating> ratingList;

    @BeforeAll
    public void setUp() {
        rating = new Rating("Moody's rating", "sandPRating", "fitch rating", 123456);
        ratingList = Collections.singletonList(rating);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Return list of ratings")
    public void homeTest() throws Exception {
        when(service.getRatings()).thenReturn(ratingList);

        mockMvc.perform(get("/rating/list"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(model().attributeExists("ratings"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Show add rating form")
    public void addRatingFormTest() throws Exception {
        mockMvc.perform(get("/rating/add"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Add new rating successful")
    public void validateTest() throws Exception {
        when(service.saveRating(rating)).thenReturn(rating);

        mockMvc.perform(post("/rating/validate")
                                .with(csrf().asHeader())
                                .param("moodysRating", rating.getMoodysRating())
                                .param("sandPRating", rating.getSandPRating())
                                .param("fitchRating", rating.getFitchRating())
                                .param("orderNumber", rating.getOrderNumber().toString()))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("success"))
               .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Add new rating with empty order number")
    public void validateTest_withInvalid_orderNumber() throws Exception {
        when(service.saveRating(rating)).thenReturn(rating);

        mockMvc.perform(post("/rating/validate")
                                .with(csrf().asHeader())
                                .param("moodysRating", rating.getMoodysRating())
                                .param("sandPRating", rating.getSandPRating())
                                .param("fitchRating", rating.getFitchRating())
                                .param("orderNumber", ""))
               .andExpect(model().hasErrors())
               .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Show update form successful")
    public void showUpdateFormIsSuccessful() throws Exception {
        when(service.getRatingById(1)).thenReturn(Optional.of(rating));
        when(service.getRatings()).thenReturn(ratingList);

        mockMvc.perform(get("/rating/update/{id}", "1"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(model().attributeExists("rating"))
               .andExpect(view().name("rating/update"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Show update form failed")
    public void showUpdateFormFails() throws Exception {
        when(service.getRatingById(3)).thenReturn(Optional.empty());
        when(service.getRatings()).thenReturn(ratingList);

        mockMvc.perform(get("/rating/update/{id}", "3"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("error"))
               .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Update rating successful")
    public void updateRatingTest() throws Exception {
        when(service.getRatingById(any(Integer.class))).thenReturn(Optional.of(rating));

        mockMvc.perform(post("/rating/update/{id}", "1")
                                .with(csrf().asHeader())
                                .param("moodysRating", "new moodys")
                                .param("sandPRating", "new s&P")
                                .param("fitchRating", "new fitch rating")
                                .param("orderNumber", "789000"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("success"))
               .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete rating successful")
    public void deleteRatingIsSuccessful() throws Exception {
        when(service.getRatingById(any(Integer.class))).thenReturn(Optional.of(rating));
        //when(service.getRatings()).thenReturn(ratingList);

        mockMvc.perform(get("/rating/delete/{id}", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("success"))
               .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete rating failed")
    public void deleteRatingFailed() throws Exception {
        when(service.getRatingById(any(Integer.class))).thenReturn(Optional.empty());
        //when(service.getRatings()).thenReturn(ratingList);

        mockMvc.perform(get("/rating/delete/{id}", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("error"))
               .andExpect(view().name("redirect:/rating/list"));
    }
}
