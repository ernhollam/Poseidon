package com.nnk.springboot.controllers.rest;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.services.CurvePointService;
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
@RequestMapping("/curvePoint")
public class CurvePointRestController {
    @Autowired
    private final CurvePointService service;

    private final String IDNotFoundMessage = "No bid found with id:";

    /**
     * Constructor.
     * @param service service layer
     */
    public CurvePointRestController(CurvePointService service) {this.service = service;}

    /**
     * Returns all bids.
     */
    @GetMapping
    public List<CurvePoint> getCurvePoints() {
        return service.getCurvePoints();
    }

    /**
     * Returns a bid if it exists.
     * @param id ID of bid to find
     * @return a bid or throws ResourceNotFoundException
     *
     * @see com.nnk.springboot.domain.CurvePoint
     * @see com.nnk.springboot.exceptions.ResourceNotFoundException
     */
    @GetMapping("/{id}")
    public CurvePoint getCurvePointByID(@PathVariable Integer id) {
        Assert.notNull(id, "ID must be provided.");
        return service.getCurvePointById(id)
                .orElseThrow(() -> new ResourceNotFoundException(IDNotFoundMessage + id +"."));
    }

    /**
     * Creates a curve point.
     * @param curvePoint curve point to create.
     * @return saved curvePoint.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CurvePoint createCurvePoint(@RequestBody CurvePoint curvePoint) {
        return service.saveCurvePoint(curvePoint);
    }

    /**
     * Updates a curvePoint.
     * @param curvePoint Curve point to update.
     * @return updated curvePoint.
     */
    @PutMapping
    public CurvePoint updateCurvePoint(@RequestBody CurvePoint curvePoint) {
        return service.updateCurvePoint(curvePoint);
    }

    /**
     * Deletes a bid.
     *
     * @param id
     *         ID of bid to delete.
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurvePoint(@PathVariable Integer id) {
        if (id == null) throw new ResourceNotFoundException(IDNotFoundMessage + id + ".");
        service.deleteCurvePoint(id);
    }
}
