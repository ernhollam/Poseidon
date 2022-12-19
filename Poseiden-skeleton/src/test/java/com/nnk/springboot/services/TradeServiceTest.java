package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
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
@Import(TradeService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TradeServiceTest {
    /**
     * Class under test.
     */
    @Autowired
    TradeService service;

    @MockBean
    TradeRepository repository;

    private Trade trade;

    @BeforeAll
    void init() {
        trade = new Trade("account", "type", 10.0);
        trade.setTradeId(1);
    }


    @Test
    public void testSaveTrade() {
        when(repository.save(trade)).thenReturn(trade);
        service.saveTrade(trade);
        verify(repository, times(1)).save(trade);
    }

    @Test
    public void testGetTradeById_successful() {
        when(repository.findById(1)).thenReturn(Optional.of(trade));
        assertEquals(Optional.of(trade), service.getTradeById(1));
    }

    @Test
    public void testGetTradeById_withNullId() {
        assertThrows(Exception.class, () -> service.getTradeById(null));
    }

    @Test
    public void testGetTrades() {
        when(repository.findAll()).thenReturn(Collections.singletonList(trade));
        assertEquals(Collections.singletonList(trade), service.getTrades());
    }

    @Test
    public void testUpdateTrade_successful() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(trade));
        service.updateTrade(trade);
        verify(repository, times(1)).save(trade);
    }

    @Test
    public void testUpdateTrade_witIdNotPresent() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.updateTrade(trade));
    }

    @Test
    public void testDeleteTrade_successful() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(trade));
        service.deleteTrade(trade.getTradeId());
        verify(repository, times(1)).deleteById(trade.getTradeId());
    }

    @Test
    public void testDeleteTrade_witIdNotPresent() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deleteTrade(1));
    }

    @Test
    public void testDeleteTrade_withNullId() {
        assertThrows(Exception.class, () -> service.deleteTrade(null));
    }
}