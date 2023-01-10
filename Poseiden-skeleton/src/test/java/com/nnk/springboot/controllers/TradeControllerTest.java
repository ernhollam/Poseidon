package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

@WebMvcTest(TradeController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class TradeControllerTest {
    @Autowired
    MockMvc      mockMvc;
    @MockBean
    TradeService service;

    private Trade       trade;
    private List<Trade> tradeList;

    @BeforeAll
    public void setUp() {
        trade = new Trade("trade account", "type", 10.0d);
        tradeList = Collections.singletonList(trade);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Return list of trades")
    public void homeTest() throws Exception {
        when(service.getTrades()).thenReturn(tradeList);

        mockMvc.perform(get("/trade/list"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(model().attributeExists("trades"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Show add trade form")
    public void addTradeFormTest() throws Exception {
        mockMvc.perform(get("/trade/add"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Add new trade successful")
    public void validateTest() throws Exception {
        when(service.saveTrade(trade)).thenReturn(trade);

        mockMvc.perform(post("/trade/validate")
                                .with(csrf().asHeader())
                                .param("account", trade.getAccount())
                                .param("type", trade.getType())
                                .param("buyQuantity", trade.getBuyQuantity().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("success"))
               .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Add new trade with null buy quantity")
    public void validateTest_withNull_buyQuantity() throws Exception {
        when(service.saveTrade(trade)).thenReturn(trade);

        mockMvc.perform(post("/trade/validate")
                                .with(csrf().asHeader())
                                .param("account", trade.getAccount())
                                .param("type", trade.getType())
                                .param("buyQuantity", "")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
               .andExpect(model().hasErrors())
               .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Show update form successful")
    public void showUpdateFormIsSuccessful() throws Exception {
        when(service.getTradeById(1)).thenReturn(Optional.of(trade));
        when(service.getTrades()).thenReturn(tradeList);

        mockMvc.perform(get("/trade/update/{id}", "1"))
               .andExpect(status().is2xxSuccessful())
               .andExpect(model().attributeExists("trade"))
               .andExpect(view().name("trade/update"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Show update form failed")
    public void showUpdateFormFails() throws Exception {
        when(service.getTradeById(3)).thenReturn(Optional.empty());
        when(service.getTrades()).thenReturn(tradeList);

        mockMvc.perform(get("/trade/update/{id}", "3"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("error"))
               .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Update trade successful")
    public void updateTradeTest() throws Exception {
        when(service.getTradeById(any(Integer.class))).thenReturn(Optional.of(trade));

        mockMvc.perform(post("/trade/update/{id}", "1")
                                .with(csrf().asHeader())
                                .param("account", trade.getAccount())
                                .param("type", trade.getType())
                                .param("buyQuantity", trade.getBuyQuantity().toString()))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("success"))
               .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete trade successful")
    public void deleteTradeIsSuccessful() throws Exception {
        when(service.getTradeById(any(Integer.class))).thenReturn(Optional.of(trade));
        //when(service.getTrades()).thenReturn(tradeList);

        mockMvc.perform(get("/trade/delete/{id}", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("success"))
               .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete trade failed")
    public void deleteTradeFailed() throws Exception {
        when(service.getTradeById(any(Integer.class))).thenReturn(Optional.empty());
        //when(service.getTrades()).thenReturn(tradeList);

        mockMvc.perform(get("/trade/delete/{id}", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("error"))
               .andExpect(view().name("redirect:/trade/list"));
    }
}
