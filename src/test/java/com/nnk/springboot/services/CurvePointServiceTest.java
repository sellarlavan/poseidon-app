package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService;

    @Test
    void testFindAll() {
        when(curvePointRepository.findAll()).thenReturn(List.of(new CurvePoint()));

        List<CurvePoint> result = curvePointService.findAll();

        assertEquals(1, result.size());
        verify(curvePointRepository).findAll();
    }

    @Test
    void testSave() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setTerm(10.0);
        curvePoint.setValue(5.0);

        when(curvePointRepository.save(any())).thenReturn(curvePoint);

        CurvePoint result = curvePointService.save(curvePoint);

        assertNotNull(result);
        assertEquals(10.0, result.getTerm());
        verify(curvePointRepository).save(curvePoint);
    }

    @Test
    void testFindById() {
        CurvePoint curvePoint = new CurvePoint(1, 10.0, 5.0);
        curvePoint.setId(1);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        CurvePoint result = curvePointService.findById(1);

        assertEquals(10.0, result.getTerm());
        verify(curvePointRepository).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> curvePointService.findById(1));
    }

    @Test
    void testUpdate() {
        CurvePoint existing = new CurvePoint(1, 5.0, 2.0);
        existing.setId(1);

        CurvePoint updated = new CurvePoint();
        updated.setTerm(10.0);
        updated.setValue(8.0);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(existing));
        when(curvePointRepository.save(any())).thenReturn(existing);

        CurvePoint result = curvePointService.update(1, updated);

        assertEquals(10.0, result.getTerm());
        assertEquals(8.0, result.getValue());
        verify(curvePointRepository).save(existing);
    }

    @Test
    void testDelete() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);

        curvePointService.delete(curvePoint);

        verify(curvePointRepository).delete(curvePoint);
    }
}
