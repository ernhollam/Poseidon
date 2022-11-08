package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Bid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BidTests {

	@Autowired
	private BidRepository bidRepository;

	@Test
	public void bidListTest() {
		Bid bid = new Bid("Account Test", "Type Test", 10d);

		// Save
		bid = bidRepository.save(bid);
		assertNotNull(bid.getBidListId());
		assertEquals(bid.getBidQuantity(), 10d, 10d);

		// Update
		bid.setBidQuantity(20d);
		bid = bidRepository.save(bid);
		assertEquals(bid.getBidQuantity(), 20d, 20d);

		// Find
		List<Bid> listResult = bidRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = bid.getBidListId();
		bidRepository.delete(bid);
		Optional<Bid> bidList = bidRepository.findById(id);
		assertFalse(bidList.isPresent());
	}
}
