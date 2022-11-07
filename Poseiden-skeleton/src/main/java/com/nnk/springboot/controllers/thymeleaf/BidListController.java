package com.nnk.springboot.controllers.thymeleaf;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Controller
@RequestMapping("/bidList")
@Slf4j
public class BidListController {
    @Autowired
    final
    BidListService bidListService;

    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    @RequestMapping("/list")
    public String home(Model model) {
        List<BidList> bidList = bidListService.getBidList();

        model.addAttribute("bidList", bidList);
        return "bidList/list";
    }

    @GetMapping("/add")
    public String addBidForm(BidList bid, Model model) {
        return "bidList/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        // check data valid and save to db, after saving return bid list
        if (result.hasErrors()) {
            log.error(Objects.requireNonNull(result.getGlobalError()).getDefaultMessage());
            return "bidList/add";
        }
        bidListService.saveBid(bid);
        return "redirect:/bidList/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // get Bid by ID and to model then show to the form
        Optional<BidList> existingBid = bidListService.getBidById(id);
        if (existingBid.isPresent()) {
            model.addAttribute("bidList", existingBid.get());
            return "bidList/update";
        }
        return "redirect:/bidList/list";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        // check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) return "bidList/update";
        bidList.setBidListId(id);
        bidListService.updateBid(bidList);
        return "redirect:/bidList/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // Find Bid by ID and delete the bid, return to Bid list
        Optional<BidList> existingBid = bidListService.getBidById(id);
        if (existingBid.isPresent()) {
            bidListService.deleteBid(id);
        }
        return "redirect:/bidList/list";
    }
}
