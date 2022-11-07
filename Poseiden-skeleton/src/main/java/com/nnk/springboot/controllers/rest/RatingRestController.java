package com.nnk.springboot.controllers.rest;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.services.RatingService;
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
@RequestMapping("/rating")
public class RatingRestController {
    @Autowired
    private final RatingService service;

    private final String IDNotFoundMessage = "No rating found with id:";

    /**
     * Constructor.
     * @param service service layer
     */
    public RatingRestController(RatingService service) {this.service = service;}

    /**
     * Returns all ratings.
     */
    @GetMapping
    public List<Rating> getRatings() {
        return service.getRatings();
    }

    /**
     * Returns a rating if it exists.
     * @param id ID of rating to find
     * @return a rating or throws ResourceNotFoundException
     *
     * @see com.nnk.springboot.domain.Rating
     * @see com.nnk.springboot.exceptions.ResourceNotFoundException
     */
    @GetMapping("/{id}")
    public Rating getRatingByID(@PathVariable Integer id) {
        Assert.notNull(id, "ID must be provided.");
        return service.getRatingById(id)
                .orElseThrow(() -> new ResourceNotFoundException(IDNotFoundMessage + id +"."));
    }

    /**
     * Creates a rating.
     * @param rating Rating to create.
     * @return saved rating.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rating createRating(@RequestBody Rating rating) {
        return service.saveRating(rating);
    }

    /**
     * Updates a rating.
     * @param rating Rating to update.
     * @return updated rating.
     */
    @PutMapping
    public Rating updateRating(@RequestBody Rating rating) {
        return service.updateRating(rating);
    }

    /**
     * Deletes a rating.
     *
     * @param id
     *         ID of rating to delete.
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRating(@PathVariable Integer id) {
        if (id == null) throw new ResourceNotFoundException(IDNotFoundMessage + id + ".");
        service.deleteRating(id);
    }
}
