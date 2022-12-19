package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@Import(CurvePointService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CurvePointServiceTest {
    /**
     * Class under test.
     */
    @Autowired
    CurvePointService service;

    @MockBean
    CurvePointRepository repository;

    private CurvePoint curvePoint;

    @BeforeAll
    void init() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        LocalDateTime now       = LocalDateTime.now();
        curvePoint = new CurvePoint(1, 10, now, 10.0, 1.0, yesterday);
    }


    @Test
    public void testSaveCurvePoint() {
        when(repository.save(curvePoint)).thenReturn(curvePoint);
        assertEquals(curvePoint, service.saveCurvePoint(curvePoint));
    }

    @Test
    public void testGetCurvePointById_successful() {
        when(repository.findById(1)).thenReturn(Optional.of(curvePoint));
        assertEquals(Optional.of(curvePoint), service.getCurvePointById(1));
    }

    @Test
    public void testGetCurvePointById_withNullId() {
        assertThrows(Exception.class, () -> service.getCurvePointById(null));
    }

    @Test
    public void testGetCurvePoints() {
        when(repository.findAll()).thenReturn(Collections.singletonList(curvePoint));
        assertEquals(Collections.singletonList(curvePoint), service.getCurvePoints());
    }

    @Test
    public void testUpdateCurvePoint_successful() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(curvePoint));
        service.updateCurvePoint(curvePoint);
        verify(repository, times(1)).save(curvePoint);
    }

    @Test
    public void testUpdateCurvePoint_witIdNotPresent() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.updateCurvePoint(curvePoint));
    }

    @Test
    public void testDeleteCurvePoint_successful() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(curvePoint));
        service.deleteCurvePoint(curvePoint.getId());
        verify(repository, times(1)).deleteById(curvePoint.getId());
    }

    @Test
    public void testDeleteCurvePoint_witIdNotPresent() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deleteCurvePoint(1));
    }

    @Test
    public void testDeleteCurvePoint_withNullId() {
        assertThrows(Exception.class, () -> service.deleteCurvePoint(null));
    }
}