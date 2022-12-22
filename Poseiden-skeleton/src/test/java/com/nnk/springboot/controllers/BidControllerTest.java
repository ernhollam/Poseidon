package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.domain.viewmodel.BidViewModel;
import com.nnk.springboot.services.BidService;
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
@WebMvcTest(controllers = BidController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BidControllerTest {
    @Autowired
    MockMvc    mockMvc;
    @MockBean
    BidService service;

    private Bid       bid;
    private List<Bid> bidList;

    @BeforeAll
    public void setUp() {
        bid = new Bid("account", "type", 10d);
        bidList = Collections.singletonList(bid);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Return list of bids")
    public void homeTest() throws Exception {
        when(service.getBids()).thenReturn(bidList);

        mockMvc.perform(get("/bidList/list"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(model().attributeExists("bidList"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Show add bid form")
    public void addBidFormTest() throws Exception {
        mockMvc.perform(get("/bidList/add"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Add new bid successful")
    public void validateTest() throws Exception {
        when(service.viewModelToEntity(any(BidViewModel.class))).thenReturn(bid);
        when(service.saveBid(bid)).thenReturn(bid);

        mockMvc.perform(post("/bidList/validate")
                                .with(csrf().asHeader())
                                .param("account", "account")
                                .param("type", "type")
                                .param("bidQuantity", "10"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("success"))
               .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Show update form successful")
    public void showUpdateFormIsSuccessful() throws Exception {
        when(service.getBidById(1)).thenReturn(Optional.of(bid));
        when(service.getBids()).thenReturn(bidList);

        mockMvc.perform(get("/bidList/update/{id}", "1"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(model().attributeExists("bid"))
               .andExpect(view().name("bidList/update"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Show update form failed")
    public void showUpdateFormFails() throws Exception {
        when(service.getBidById(3)).thenReturn(Optional.empty());
        when(service.getBids()).thenReturn(bidList);

        mockMvc.perform(get("/bidList/update/{id}", "3"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("error"))
               .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Update bid successful")
    public void updateBidTest() throws Exception {
        when(service.getBidById(any(Integer.class))).thenReturn(Optional.of(bid));
        when(service.updateBid(bid)).thenReturn(bid);
        when(service.getBids()).thenReturn(bidList);

        mockMvc.perform(post("/bidList/update/{id}", "1")
                                .with(csrf().asHeader())
                                .param("account", "account")
                                .param("type", "type")
                                .param("bidQuantity", "10"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("success"))
               .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete bid successful")
    public void deleteBidIsSuccessful() throws Exception {
        when(service.getBidById(any(Integer.class))).thenReturn(Optional.of(bid));
        when(service.getBids()).thenReturn(bidList);

        mockMvc.perform(get("/bidList/delete/{id}", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("success"))
               .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete bid failed")
    public void deleteBidFailed() throws Exception {
        when(service.getBidById(any(Integer.class))).thenReturn(Optional.empty());
        when(service.getBids()).thenReturn(bidList);

        mockMvc.perform(get("/bidList/delete/{id}", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("error"))
               .andExpect(view().name("redirect:/bidList/list"));
    }
}
