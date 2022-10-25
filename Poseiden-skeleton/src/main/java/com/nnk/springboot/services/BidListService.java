package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        bidList.setRevisionDate(LocalDateTime.now());
        return bidListRepository.save(bidList);
    }

    /**
     * Deletes a bid.
     *
     * @param id
     *         ID of bid to delete.
     */
    public void deleteBid(final Integer id) {
        bidListRepository.deleteById(id);
    }

}
