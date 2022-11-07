package com.nnk.springboot.controllers.rest;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.services.TradeService;
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
@RequestMapping("/trade")
public class TradeRestController {
    @Autowired
    private final TradeService service;

    private final String IDNotFoundMessage = "No trade found with id:";

    /**
     * Constructor.
     * @param service service layer
     */
    public TradeRestController(TradeService service) {this.service = service;}

    /**
     * Returns all trades.
     */
    @GetMapping
    public List<Trade> getTrades() {
        return service.getTrades();
    }

    /**
     * Returns a trade if it exists.
     * @param id ID of trade to find
     * @return a trade or throws ResourceNotFoundException
     *
     * @see com.nnk.springboot.domain.Trade
     * @see com.nnk.springboot.exceptions.ResourceNotFoundException
     */
    @GetMapping("/{id}")
    public Trade getRuleByID(@PathVariable Integer id) {
        Assert.notNull(id, "ID must be provided.");
        return service.getTradeById(id)
                .orElseThrow(() -> new ResourceNotFoundException(IDNotFoundMessage + id +"."));
    }

    /**
     * Creates a trade.
     * @param trade Trade to create.
     * @return saved trade.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trade createRule(@RequestBody Trade trade) {
        return service.saveTrade(trade);
    }

    /**
     * Updates a trade.
     * @param trade Trade to update.
     * @return updated trade.
     */
    @PutMapping
    public Trade updateRule(@RequestBody Trade trade) {
        return service.updateTrade(trade);
    }

    /**
     * Deletes a trade.
     *
     * @param id
     *         ID of trade to delete.
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRule(@PathVariable Integer id) {
        if (id == null) throw new ResourceNotFoundException(IDNotFoundMessage + id + ".");
        service.deleteTrade(id);
    }
}
