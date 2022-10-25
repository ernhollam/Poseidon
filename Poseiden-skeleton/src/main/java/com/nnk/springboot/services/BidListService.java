package com.nnk.springboot.services;

import com.nnk.springboot.constants.Pagination;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class BidListService {
    @Autowired
    BidListRepository bidListRepository;
    @Autowired
    PaginationService paginationService;

    public BidListService(BidListRepository bidListRepository, PaginationService paginationService) {
        this.bidListRepository = bidListRepository;
        this.paginationService = paginationService;
    }

    /**
     * Creates new bid.
     */
    public BidList saveBid(final BidList bidList) {
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
    public Page<?> getBidList() {
        return paginationService
                .getPaginatedList(PageRequest.of(Pagination.DEFAULT_PAGE, Pagination.DEFAULT_SIZE),
                                  bidListRepository.findAll());
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
