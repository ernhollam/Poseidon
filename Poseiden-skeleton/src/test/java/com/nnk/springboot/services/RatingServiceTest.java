package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@Import(RatingService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RatingServiceTest {
    /**
     * Class under test.
     */
    @Autowired
    RatingService service;

    @MockBean
    RatingRepository repository;

    private Rating rating;

    @BeforeAll
    void init() {
        rating = new Rating(1, "moody's rating", "s&p rating", "fitch rating", 123456);
    }


    @Test
    public void testSaveRating() {
        when(repository.save(rating)).thenReturn(rating);
        service.saveRating(rating);
        verify(repository, times(1)).save(rating);
    }

    @Test
    public void testGetRatingById_successful() {
        when(repository.findById(1)).thenReturn(Optional.of(rating));
        assertEquals(Optional.of(rating), service.getRatingById(1));
    }

    @Test
    public void testGetRatingById_withNullId() {
        assertThrows(Exception.class, () -> service.getRatingById(null));
    }

    @Test
    public void testGetRatings() {
        when(repository.findAll()).thenReturn(Collections.singletonList(rating));
        assertEquals(Collections.singletonList(rating), service.getRatings());
    }

    @Test
    public void testUpdateRating_successful() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(rating));
        service.updateRating(rating);
        verify(repository, times(1)).save(rating);
    }

    @Test
    public void testUpdateRating_witIdNotPresent() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.updateRating(rating));
    }

    @Test
    public void testDeleteRating_successful() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(rating));
        service.deleteRating(rating.getId());
        verify(repository, times(1)).deleteById(rating.getId());
    }

    @Test
    public void testDeleteRating_witIdNotPresent() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deleteRating(1));
    }

    @Test
    public void testDeleteRating_withNullId() {
        assertThrows(Exception.class, () -> service.deleteRating(null));
    }
}