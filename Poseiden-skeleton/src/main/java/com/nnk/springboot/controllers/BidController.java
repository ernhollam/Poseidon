package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.services.BidService;
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
@RequestMapping("/bidList")
@Slf4j
public class BidController {
    @Autowired
    private BidService bidService;


    /**
     * Shows list of bids.
     */
    @RequestMapping("/list")
    public String home(Model model, Principal user) {
        model.addAttribute("bidList", bidService.getBids());
        model.addAttribute("username", UserService.getUsername(user));
        return "bidList/list";
    }

    /**
     * Shows page to add new bid.
     *
     * @return html page to add bid
     */
    @GetMapping("/add")
    public String addBidForm(Bid bid) {
        return "bidList/add";
    }

    /**
     * Validates fields in Add new bid form.
     *
     * @param bid
     *         fields to be validated
     * @param result
     *         result of validation
     *
     * @return Returns the list of bids if the form is valid, throws an error otherwise
     */
    @PostMapping("/validate")
    public String validate(@Valid Bid bid, BindingResult result, Model model,
                           RedirectAttributes redirectAttributes) {
        // check data valid and save to db, after saving return bid list
        if (!result.hasErrors()) {
            // save new bid
            bidService.saveBid(bid);
            redirectAttributes.addFlashAttribute("success", "Bid was successfully created.");
            model.addAttribute("bidList", bidService.getBids());
            // redirect to list of bids page
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    /**
     * Updates a bid
     *
     * @param id
     *         ID of bid to update
     * @param model
     *         holder for context data to be passed from controller to the view
     *
     * @return update bid page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        // get Bid by ID and to model then show to the form
        Optional<Bid> existingBid = bidService.getBidById(id);
        if (existingBid.isPresent()) {
            model.addAttribute("bid", existingBid.get());
            return "bidList/update";
        }
        redirectAttributes.addFlashAttribute("error", "Provided bid with ID " + id + " does not exist.");
        return "redirect:/bidList/list";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid Bid bid,
                            BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        // check required fields
        if (result.hasErrors()) return "bidList/update";
        // if valid call service to update Bid
        bid.setBidListId(id);
        bidService.updateBid(bid);
        // add redirect message
        redirectAttributes.addFlashAttribute("success", "Bid with ID " + id + " was successfully updated.");
        // update list and return list Bid
        model.addAttribute("bids", bidService.getBids());
        return "redirect:/bidList/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        // Find Bid by ID
        Optional<Bid> existingBid = bidService.getBidById(id);
        // and delete the bid
        if (existingBid.isPresent()) {
            bidService.deleteBid(id);
            redirectAttributes.addFlashAttribute("success", "Bid with ID " + id + " was successfully deleted.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Provided bid with ID " + id + " does not exist.");
        }
        // return to Bid list
        model.addAttribute("bids", bidService.getBids());
        return "redirect:/bidList/list";
    }
}
