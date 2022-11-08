package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.viewmodel.BidViewModel;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.repositories.BidRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BidService {
    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private ModelMapper modelMapper;


    /**
     * Creates new bid.
     */
    public BidList saveBid(final BidList bidList) {
        bidList.setCreationDate(LocalDateTime.now());
        return bidRepository.save(bidList);
    }

    /**
     * Gets a bid.
     *
     * @param id
     *         ID of bid list to find
     *
     * @return Optional bid list object.
     */
    public Optional<BidList> getBidById(final Integer id) {
        Assert.notNull(id, "ID must not be null.");
        return bidRepository.findById(id);
    }

    /**
     * Returns list of all bids.
     */
    public List<BidList> getBids() {
        return bidRepository.findAll();
    }

    /**
     * Updates a bid.
     *
     * @param bidList
     *         bid to update
     *
     * @return updated bid object.
     */
    public BidList updateBid(final BidList bidList) {
        if (bidRepository.findById(bidList.getBidListId()).isPresent()) {
            bidList.setRevisionDate(LocalDateTime.now());
            return bidRepository.save(bidList);
        } else {
            log.error("Provided bid with ID " + bidList.getBidListId() + "does not exist.");
            throw new ResourceNotFoundException("Provided bid with ID " + bidList.getBidListId() + "does not exist.");
        }
    }

    /**
     * Deletes a bid.
     *
     * @param id
     *         ID of bid to delete.
     */
    public void deleteBid(final Integer id) {
        Assert.notNull(id, "ID must not be null.");
        if (bidRepository.findById(id).isPresent()) {
            bidRepository.deleteById(id);
            log.debug("Deleted bid with ID "+ id +".");
        } else {
            log.error("There is no such bid with ID " + id + ".");
            throw new ResourceNotFoundException("There is no such bid with ID " + id + ".");
        }
    }

    public BidList viewModelToEntity(BidViewModel bidViewModel) {
        return modelMapper.map(bidViewModel, BidList.class);
    }

    public BidViewModel entityToViewModel(BidList bidList) {
        return modelMapper.map(bidList, BidViewModel.class);
    }
}
