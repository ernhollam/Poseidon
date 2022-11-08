package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.viewmodel.BidViewModel;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@Import(BidService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BidServiceTest {
    /**
     * Class under test.
     */
    @Autowired
    BidService bidService;

    @MockBean
    BidRepository bidRepository;
    @MockBean
    ModelMapper modelMapper;

    private BidList      bidList;
    private BidViewModel bidViewModel;

    @BeforeAll
    void init() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        LocalDateTime now       = LocalDateTime.now();
        bidList = new BidList(1, "bid list account", "bid type", 3, 1, 1, 1, "benchmark", now, "comment",
                              "security", "status", "trader", "book", "creation name", yesterday,
                              "revision name", now, "deal", "deal type", "source 1", "side");

        bidViewModel = new BidViewModel("view model account", "view model type", 2);
    }

    @Test
    @DisplayName("Conversion entity to view model")
    public void viewModelToEntity() {
        BidList expected = new BidList(bidViewModel.getAccount(),
                                       bidViewModel.getType(), bidViewModel.getBidQuantity());
        when(modelMapper.map(bidViewModel, BidList.class)).thenReturn(expected);

        BidList result = bidService.viewModelToEntity(bidViewModel);

        assertEquals(result.getAccount(), bidViewModel.getAccount());
        assertEquals(result.getType(), bidViewModel.getType());
        assertEquals(result.getBidQuantity(), bidViewModel.getBidQuantity());
    }

    @Test
    @DisplayName("Conversion  view model to entity")
    public void entityToViewModel(){
        BidViewModel expected = new BidViewModel(bidList.getAccount(), bidList.getType(), bidList.getBidQuantity());
        when(modelMapper.map(bidList, BidViewModel.class)).thenReturn(expected);
        BidViewModel result = bidService.entityToViewModel(bidList);

        assertEquals(result.getAccount(), bidList.getAccount());
        assertEquals(result.getType(), bidList.getType());
        assertEquals(result.getBidQuantity(), bidList.getBidQuantity());
    }

}