package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BidListService {
    @Autowired
    final
    BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    /**
     * Creates new bid.
     */
    public BidList saveBid(final BidList bidList) {
        bidList.setCreationDate(LocalDateTime.now());
        return bidListRepository.save(bidList);
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
        return bidListRepository.findById(id);
    }

    /**
     * Returns list of all bids.
     */
    public List<BidList> getBidList() {
        return bidListRepository.findAll();
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
        if (bidListRepository.findById(bidList.getBidListId()).isPresent()) {
            bidList.setRevisionDate(LocalDateTime.now());
            return bidListRepository.save(bidList);
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
        if (bidListRepository.findById(id).isPresent()) {
            bidListRepository.deleteById(id);
            log.debug("Deleted bid with ID "+ id +".");
        } else {
            log.error("There is no such bid with ID " + id + ".");
            throw new ResourceNotFoundException("There is no such bid with ID " + id + ".");
        }
    }

}
