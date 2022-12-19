package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RatingService {
    @Autowired
    final
    RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * Creates new rating.
     */
    public Rating saveRating(final Rating rating) {
        log.debug("Saving new rating: " + rating);
        return ratingRepository.save(rating);
    }

    /**
     * Gets a rating.
     *
     * @param id
     *         ID of rating to find
     *
     * @return Optional rating object.
     */
    public Optional<Rating> getRatingById(final Integer id) {
        Assert.notNull(id, "ID must not be null.");
        return ratingRepository.findById(id);
    }

    /**
     * Returns list of all ratings.
     */
    public List<Rating> getRatings() {
        return ratingRepository.findAll();
    }

    /**
     * Updates a rating.
     *
     * @param rating
     *         rating to update
     *
     * @return updated rating object.
     */
    public Rating updateRating(final Rating rating) {
        if (ratingRepository.findById(rating.getId()).isPresent()) {
            return ratingRepository.save(rating);
        } else {
            log.error("Provided rating with ID " + rating.getId() + " does not exist.");
            throw new ResourceNotFoundException("Provided rating with ID " + rating.getId() + " does not exist.");
        }
    }

    /**
     * Deletes a rating.
     *
     * @param id
     *         ID of rating to delete.
     */
    public void deleteRating(final Integer id) {
        Assert.notNull(id, "ID must not be null.");
        if (ratingRepository.findById(id).isPresent()) {
            ratingRepository.deleteById(id);
            log.debug("Deleted rating with ID "+ id +".");
        } else {
            log.error("There is no such rating with ID " + id + ".");
            throw new ResourceNotFoundException("There is no such rating with ID " + id + ".");
        }
    }

}
