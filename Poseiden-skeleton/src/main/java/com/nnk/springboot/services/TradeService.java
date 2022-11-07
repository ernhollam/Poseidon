package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TradeService {
    @Autowired
    final
    TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    /**
     * Creates new trade.
     */
    public Trade saveTrade(final Trade trade) {
        trade.setCreationDate(LocalDateTime.now());
        return tradeRepository.save(trade);
    }

    /**
     * Gets a trade.
     *
     * @param id
     *         ID of trade to find
     *
     * @return Optional trade object.
     */
    public Optional<Trade> getTradeById(final Integer id) {
        Assert.notNull(id, "ID must not be null.");
        return tradeRepository.findById(id);
    }

    /**
     * Returns list of all trades.
     */
    public List<Trade> getTrades() {
        return tradeRepository.findAll();
    }

    /**
     * Updates a trade.
     *
     * @param trade
     *         trade to update
     *
     * @return updated trade object.
     */
    public Trade updateTrade(final Trade trade) {
        if (tradeRepository.findById(trade.getTradeId()).isPresent()) {
            trade.setRevisionDate(LocalDateTime.now());
            return tradeRepository.save(trade);
        } else {
            log.error("Provided trade with ID " + trade.getTradeId() + "does not exist.");
            throw new ResourceNotFoundException("Provided trade with ID " + trade.getTradeId() + "does not exist.");
        }
    }

    /**
     * Deletes a trade.
     *
     * @param id
     *         ID of trade to delete.
     */
    public void deleteTrade(final Integer id) {
        Assert.notNull(id, "ID must not be null.");
        if (tradeRepository.findById(id).isPresent()) {
            tradeRepository.deleteById(id);
            log.debug("Deleted trade with ID "+ id +".");
        } else {
            log.error("There is no such trade with ID " + id + ".");
            throw new ResourceNotFoundException("There is no such trade with ID " + id + ".");
        }
    }

}
