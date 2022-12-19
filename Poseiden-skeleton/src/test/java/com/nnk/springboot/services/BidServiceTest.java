package com.nnk.springboot.services;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.domain.viewmodel.BidViewModel;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.repositories.BidRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@Import(BidService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BidServiceTest {
    /**
     * Class under test.
     */
    @Autowired
    BidService service;

    @MockBean
    BidRepository repository;
    @MockBean
    ModelMapper   modelMapper;

    private Bid          bid;
    private BidViewModel bidViewModel;

    @BeforeAll
    void init() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        LocalDateTime now       = LocalDateTime.now();
        bid = new Bid(1, "bid list account", "bid type", 3.0, 1.0,
                      1.0, 1.0, "benchmark", now, "comment",
                      "security", "status", "trader", "book", "creation name", yesterday,
                      "revision name", now, "deal", "deal type", "source 1", "side");

        bidViewModel = new BidViewModel("view model account", "view model type", 2.0);
    }

    @Test
    @DisplayName("Conversion entity to view model")
    public void viewModelToEntity() {
        Bid expected = new Bid(bidViewModel.getAccount(),
                               bidViewModel.getType(), bidViewModel.getBidQuantity());
        when(modelMapper.map(bidViewModel, Bid.class)).thenReturn(expected);

        Bid result = service.viewModelToEntity(bidViewModel);

        assertEquals(result.getAccount(), bidViewModel.getAccount());
        assertEquals(result.getType(), bidViewModel.getType());
        assertEquals(result.getBidQuantity(), bidViewModel.getBidQuantity());
    }

    @Test
    @DisplayName("Conversion  view model to entity")
    public void entityToViewModel() {
        BidViewModel expected = new BidViewModel(bid.getAccount(), bid.getType(), bid.getBidQuantity());
        when(modelMapper.map(bid, BidViewModel.class)).thenReturn(expected);
        BidViewModel result = service.entityToViewModel(bid);

        assertEquals(result.getAccount(), bid.getAccount());
        assertEquals(result.getType(), bid.getType());
        assertEquals(result.getBidQuantity(), bid.getBidQuantity());
    }

    @Test
    public void testSaveBid() {
        when(repository.save(bid)).thenReturn(bid);
        assertEquals(bid, service.saveBid(bid));
    }

    @Test
    public void testGetBidById_successful() {
        when(repository.findById(1)).thenReturn(Optional.of(bid));
        assertEquals(Optional.of(bid), service.getBidById(1));
    }

    @Test
    public void testGetBidById_withNullId() {
        assertThrows(Exception.class, () -> service.getBidById(null));
    }

    @Test
    public void testGetBids() {
        when(repository.findAll()).thenReturn(Collections.singletonList(bid));
        assertEquals(Collections.singletonList(bid), service.getBids());
    }

    @Test
    public void testUpdateBid_successful() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(bid));
        service.updateBid(bid);
        verify(repository, times(1)).save(bid);
    }

    @Test
    public void testUpdateBid_witIdNotPresent() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.updateBid(bid));
    }

    @Test
    public void testDeleteBid_successful() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(bid));
        service.deleteBid(bid.getBidListId());
        verify(repository, times(1)).deleteById(bid.getBidListId());
    }

    @Test
    public void testDeleteBid_witIdNotPresent() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deleteBid(1));
    }

    @Test
    public void testDeleteBid_withNullId() {
        assertThrows(Exception.class, () -> service.deleteBid(null));
    }
}