package com.nnk.springboot.controllers.rest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bidList")
public class BidRestController {
    @Autowired
    private final BidListService service;

    private final String IDNotFoundMessage = "No bid found with id:";

    /**
     * Constructor.
     * @param service service layer
     */
    public BidRestController(BidListService service) {this.service = service;}

    /**
     * Returns all bids.
     */
    @GetMapping
    public List<BidList> getBids() {
        return service.getBidList();
    }

    /**
     * Returns a bid if it exists.
     * @param id ID of bid to find
     * @return a bid or throws ResourceNotFoundException
     *
     * @see com.nnk.springboot.domain.BidList
     * @see com.nnk.springboot.exceptions.ResourceNotFoundException
     */
    @GetMapping("/{id}")
    public BidList getBidByID(@PathVariable Integer id) {
        Assert.notNull(id, "ID must be provided.");
        return service.getBidById(id)
                .orElseThrow(() -> new ResourceNotFoundException(IDNotFoundMessage + id +"."));
    }

    /**
     * Creates a bid.
     * @param bid Bid to create.
     * @return saved bid.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BidList createBid(@RequestBody BidList bid) {
        return service.saveBid(bid);
    }

    /**
     * Updates a bid.
     * @param bid Bid to update.
     * @return updated bid.
     */
    @PutMapping
    public BidList updateBid(@RequestBody BidList bid) {
        return service.updateBid(bid);
    }

    /**
     * Deletes a bid.
     *
     * @param id
     *         ID of bid to delete.
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBid(@PathVariable Integer id) {
        if (id == null) throw new ResourceNotFoundException(IDNotFoundMessage + id + ".");
        service.deleteBid(id);
    }
}
