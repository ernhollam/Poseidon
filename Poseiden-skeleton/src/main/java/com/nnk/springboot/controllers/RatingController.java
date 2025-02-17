package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
@RequestMapping("/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    /**
     * Returns list of ratings.
     *
     * @param model
     *         holder for context data to be passed from controller to the view
     *
     * @return list of ratings page
     */
    @RequestMapping("/list")
    public String home(Model model, Principal user) {
        // find all Rating, add to model
        model.addAttribute("ratings", ratingService.getRatings());
        model.addAttribute("username", UserService.getUsername(user));
        return "rating/list";
    }

    /**
     * Shows page to add new rating.
     *
     * @param rating
     *         rating to create
     *
     * @return add rating page
     */
    @GetMapping("/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * Validates the files in the add rating form.
     *
     * @param rating
     *         rating to add
     * @param result
     *         result of validation
     *
     * @return list of ratings page
     */
    @PostMapping("/validate")
    public String validate(@Valid Rating rating, BindingResult result,
                           Model model, RedirectAttributes redirectAttributes) {
        // check data valid and save to db
        if (!result.hasErrors()) {
            ratingService.saveRating(rating);
            redirectAttributes.addFlashAttribute("success", "Rating was successfully created.");
            // after saving return Rating list
            model.addAttribute("ratings", ratingService.getRatings());
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    /**
     * Shows update rating form
     *
     * @param id
     *         ID of rating to update
     * @param model
     *         holder for context data to be passed from controller to the view
     *
     * @return update rating page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        // get Rating by ID
        Optional<Rating> existingRating = ratingService.getRatingById(id);
        if (existingRating.isPresent()) {
            // and to model then show to the form
            model.addAttribute("rating", existingRating.get());
            return "rating/update";
        }
        redirectAttributes.addFlashAttribute("error", "Provided rating with ID " + id + " does not exist.");
        return "redirect:/rating/list";
    }

    /**
     * Validates the fields in update rating form.
     *
     * @param id
     *         ID of rating to be updated.
     * @param rating
     *         updated fields of existing rating
     * @param result
     *         Result of validation
     *
     * @return update rating if successful, list of ratings otherwise
     */
    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        // check required fields
        if (result.hasErrors()){
            rating.setId(id);
            model.addAttribute("rating", rating);
            return "rating/update";
        }
        // if valid call service to update Rating
        ratingService.updateRating(rating);
        // and return Rating list
        redirectAttributes.addFlashAttribute("success", "Rating with ID " + id + " was successfully updated.");
        model.addAttribute("ratings", ratingService.getRatings());
        return "redirect:/rating/list";
    }

    /**
     * Deletes a rating
     *
     * @param id
     *         ID of rating to be deleted
     *
     * @return list of ratings page
     */
    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        // Find Rating by ID
        Optional<Rating> existingRating = ratingService.getRatingById(id);
        // and delete the Rating
        if (existingRating.isPresent()) {
            ratingService.deleteRating(id);
            redirectAttributes.addFlashAttribute("success", "Rating with ID " + id + " was successfully deleted.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Provided rating with ID " + id + " does not exist.");
        }
        // return to Rating list
        model.addAttribute("ratings", ratingService.getRatings());
        return "redirect:/rating/list";
    }
}
