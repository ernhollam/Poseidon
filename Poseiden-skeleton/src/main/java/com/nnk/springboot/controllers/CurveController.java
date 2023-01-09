package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import com.nnk.springboot.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/curvePoint")
public class CurveController {
    @Autowired
    private CurvePointService curvePointService;

    /**
     * Returns list of curve points.
     *
     * @param model
     *         holder for context data to be passed from controller to the view
     *
     * @return list of curve points page
     */
    @RequestMapping("/list")
    public String home(Model model, Principal user) {
        // find all Curve Point, add to model
        model.addAttribute("curvePoints", curvePointService.getCurvePoints());
        model.addAttribute("username", UserService.getUsername(user));
        return "curvePoint/list";
    }

    /**
     * Shows page to add new curve point.
     *
     * @param curvePoint
     *         curve point to create
     *
     * @return add curve point page
     */
    @GetMapping("/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    /**
     * Validates the files in the add curve point form.
     *
     * @param curvePoint
     *         curve point to add
     * @param result
     *         result of validation
     * @param model
     *         holder for context data to be passed from controller to the view
     *
     * @return list of curve points page
     */
    @PostMapping("/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model,
                           RedirectAttributes redirectAttributes) {
        // check data valid and save to db after saving return Curve list
        if (!result.hasErrors()) {
            curvePointService.saveCurvePoint(curvePoint);
            redirectAttributes.addFlashAttribute("success", "Curve point was successfully created.");
            model.addAttribute("curvePoints", curvePointService.getCurvePoints());
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    /**
     * Shows update curve point form
     *
     * @param id
     *         ID of curve point to update
     * @param model
     *         holder for context data to be passed from controller to the view
     *
     * @return update curve point page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        // get CurvePoint by ID
        Optional<CurvePoint> existingCurvePoint = curvePointService.getCurvePointById(id);
        if (existingCurvePoint.isPresent()) {
            // and to model then show to the form
            model.addAttribute("curvePoint", existingCurvePoint.get());
            return "curvePoint/update";
        }
        redirectAttributes.addFlashAttribute("error", "Provided curve point with ID " + id + " does not exist.");
        return "redirect:/curvePoint/list";
    }

    /**
     * Validates the fields in update curve point form.
     *
     * @param id
     *         ID of curve point to be updated.
     * @param curvePoint
     *         updated fields of existing curve point
     * @param result
     *         Result of validation
     *
     * @return update curve point if successful, list of curve points otherwise
     */
    @PostMapping("/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                                   BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        // check required fields
        if (result.hasErrors()){
            curvePoint.setId(id);
            model.addAttribute("curve", curvePoint);
            return "curvePoint/update";
        }
        // if valid call service to update Curve and return Curve list
        curvePointService.updateCurvePoint(curvePoint);
        // add redirect message
        redirectAttributes.addFlashAttribute("success", "Curve point with ID " + id + " was successfully updated.");
        model.addAttribute("curvePoints", curvePointService.getCurvePoints());
        return "redirect:/curvePoint/list";
    }

    /**
     * Deletes a curve point
     *
     * @param id
     *         ID of curve point to be deleted
     *
     * @return list of curve points page
     */
    @GetMapping("/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        // Find Curve by ID and delete the Curve
        Optional<CurvePoint> existingCurvePoint = curvePointService.getCurvePointById(id);
        if (existingCurvePoint.isPresent()) {
            curvePointService.deleteCurvePoint(id);
            redirectAttributes.addFlashAttribute("success", "Curve point with ID " + id + " was successfully deleted.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Provided curve point with ID " + id + " does not exist.");
        }
        // return to Curve list
        model.addAttribute("curvePoints", curvePointService.getCurvePoints());
        return "redirect:/curvePoint/list";
    }
}
