package com.nnk.springboot.controllers.thymeleaf;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.domain.viewmodel.BidViewModel;
import com.nnk.springboot.services.BidService;
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
import java.util.Objects;
import java.util.Optional;


@Controller
@RequestMapping("/bidList")
@Slf4j
public class BidListController {
    @Autowired
    private BidService bidService;


    /**
     * Shows list of bids.
     */
    @RequestMapping("/list")
    public String home(Model model) {
        model.addAttribute("bidList", bidService.getBids());
        return "bidList/list";
    }

    /**
     * Shows page to add new bid.
     * @param bid bid to add
     * @param model holder for context data to be passed from controller to the view
     * @return html page to add bid
     */
    @GetMapping("/add")
    public String addBidForm(BidViewModel bid, Model model) {
        return "bidList/add";
    }

    /**
     * Validates fields in Add new bid form.
     * @param bid fields to be validated
     * @param result result of validation
     * @param model holder for context data to be passed from controller to the view
     * @return Returns the list of bids if the form is valid, throws an error otherwise
     */
    @PostMapping("/validate")
    public String validate(@Valid BidViewModel bid,
                           BindingResult result, Model model, RedirectAttributes redirAttrs) {
        // check data valid and save to db, after saving return bid list
        if (result.hasErrors()) {
            String error = Objects.requireNonNull(result.getGlobalError()).getDefaultMessage();
            log.error(error);
            redirAttrs.addFlashAttribute("error", error);
            return "bidList/add";
        }
        // save new bid
        bidService.saveBid(bidService.viewModelToEntity(bid));
        redirAttrs.addFlashAttribute("success", "Bid was successfully created.");
        // update list of bids
        model.addAttribute("bidList", bidService.getBids());
        // redirect to list of bids page
        return "redirect:/bidList/list";
    }

    /**
     * Updates a bid
     * @param id ID of bid to update
     * @param model holder for context data to be passed from controller to the view
     * @return update bid page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirAttrs) {
        // get Bid by ID and to model then show to the form
        Optional<Bid> existingBid = bidService.getBidById(id);
        if (existingBid.isPresent()) {
            model.addAttribute("bidList", existingBid.get());
            return "bidList/update";
        }
        redirAttrs.addFlashAttribute("error", "Provided bid with ID " + id+ "does not exist.");
        return "redirect:/bidList/list";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid Bid bid,
                            BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        // check required fields
        if (result.hasErrors()) return "bid/update";
        // if valid call service to update Bid
        bid.setBidListId(id);
        bidService.updateBid(bid);
        // add redirect message
        redirectAttributes.addFlashAttribute("success", "Bid with ID " + id + " was successfully updated.");
        // update list and return list Bid
        model.addAttribute("bid", bidService.getBids());
        return "redirect:/bid/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        // Find Bid by ID
        Optional<Bid> existingBid = bidService.getBidById(id);
        // and delete the bid
        if (existingBid.isPresent()) {
            try {
                bidService.deleteBid(id);
                redirectAttributes.addFlashAttribute("success", "Bid with ID " + id + " was successfully deleted.");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            }
        }
        // return to Bid list
        model.addAttribute("bidList", bidService.getBids());
        return "redirect:/bidList/list";
    }
}
