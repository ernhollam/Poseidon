package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CurvePointService {
    @Autowired
    final
    CurvePointRepository curvePointRepository;

    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * Creates new curve point.
     */
    public CurvePoint saveCurvePoint(final CurvePoint curvePoint) {
        curvePoint.setCreationDate(LocalDateTime.now());
        return curvePointRepository.save(curvePoint);
    }

    /**
     * Gets a curve point.
     *
     * @param id
     *         ID of curve point to find
     *
     * @return Optional curve point object.
     */
    public Optional<CurvePoint> getCurvePointById(final Integer id) {
        Assert.notNull(id, "ID must not be null.");
        return curvePointRepository.findById(id);
    }

    /**
     * Returns list of all bids.
     */
    public List<CurvePoint> getCurvePoints() {
        return curvePointRepository.findAll();
    }

    /**
     * Updates a curve point.
     *
     * @param curvePoint
     *         curve point to update
     *
     * @return updated curve point object.
     */
    public CurvePoint updateCurvePoint(final CurvePoint curvePoint) {
        if (curvePointRepository.findById(curvePoint.getId()).isPresent()) {
            return curvePointRepository.save(curvePoint);
        } else {
            log.error("Provided curve point with ID " + curvePoint.getCurveId() + "does not exist.");
            throw new ResourceNotFoundException("Provided curve point with ID " + curvePoint.getCurveId() + "does not exist.");
        }
    }

    /**
     * Deletes a curve point.
     *
     * @param id
     *         ID of curve point to delete.
     */
    public void deleteCurvePoint(final Integer id) {
        Assert.notNull(id, "ID must not be null.");
        if (curvePointRepository.findById(id).isPresent()) {
            curvePointRepository.deleteById(id);
            log.debug("Deleted curve point with ID "+ id +".");
        } else {
            log.error("There is no such curve point with ID " + id + ".");
            throw new ResourceNotFoundException("There is no such curve point with ID " + id + ".");
        }
    }

}
