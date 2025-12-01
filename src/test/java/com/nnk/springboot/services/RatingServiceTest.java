package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    @Test
    void testFindAll() {
        when(ratingRepository.findAll()).thenReturn(List.of(new Rating()));

        List<Rating> result = ratingService.findAll();

        assertEquals(1, result.size());
        verify(ratingRepository).findAll();
    }

    @Test
    void testSave() {
        Rating rating = new Rating("Moodys Rating","SandP Rating", "FitchRating", 1);

        when(ratingRepository.save(any())).thenReturn(rating);

        Rating result = ratingService.save(rating);

        assertNotNull(result);
        assertEquals("Moodys Rating", result.getMoodysRating());
        verify(ratingRepository).save(rating);
    }

    @Test
    void testFindById() {
        Rating rating = new Rating("Moodys Rating","SandP Rating", "FitchRating", 1);
        rating.setId(1);

        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        Rating result = ratingService.findById(1);

        assertEquals("Moodys Rating", result.getMoodysRating());
        verify(ratingRepository).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> ratingService.findById(1));
    }

    @Test
    void testUpdate() {
        Rating existing = new Rating("Moodys Rating","SandP Rating", "FitchRating", 1);
        existing.setId(1);

        Rating updated = new Rating("New Moodys Rating","New SandP Rating", "New Fitch Rating", 10);

        when(ratingRepository.findById(1)).thenReturn(Optional.of(existing));
        when(ratingRepository.save(any())).thenReturn(existing);

        Rating result = ratingService.update(1, updated);

        assertEquals("New Moodys Rating", result.getMoodysRating());
        assertEquals("New SandP Rating", result.getSandPRating());
        assertEquals("New Fitch Rating", result.getFitchRating());
        assertEquals(10, result.getOrderNumber());
        verify(ratingRepository).save(existing);
    }

    @Test
    void testDelete() {
        Rating rating = new Rating();
        rating.setId(1);

        ratingService.delete(rating);

        verify(ratingRepository).delete(rating);
    }
}
