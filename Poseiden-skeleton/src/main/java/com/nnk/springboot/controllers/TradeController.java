package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
@RequestMapping("/trade")
public class TradeController {
    @Autowired
    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {this.tradeService = tradeService;}

    /**
     * Returns list of trades.
     *
     * @param model
     *         holder for context data to be passed from controller to the view
     *
     * @return list of trades page
     */
    @RequestMapping("/list")
    public String home(Model model, Principal user) {
        // find all Trade, add to model
        model.addAttribute("trades", tradeService.getTrades());
        model.addAttribute("username", UserService.getUsername(user));
        return "trade/list";
    }

    /**
     * Shows page to add new trade.
     *
     * @param trade
     *         trade to create
     *
     * @return add trade page
     */
    @GetMapping("/add")
    public String addUser(Trade trade) {
        return "trade/add";
    }

    /**
     * Validates the files in the add trade form.
     *
     * @param trade
     *         trade to add
     * @param result
     *         result of validation
     *
     * @return list of trades page
     */
    @PostMapping("/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model,
                           RedirectAttributes redirectAttributes) {
        // check data valid
        if (!result.hasErrors()) {
            // and save to db
            tradeService.saveTrade(trade);
            redirectAttributes.addFlashAttribute("success", "Trade was successfully created.");
            model.addAttribute("trades", tradeService.getTrades());
            // after saving return Trade list
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    /**
     * Shows update trade form
     *
     * @param id
     *         ID of trade to update
     * @param model
     *         holder for context data to be passed from controller to the view
     *
     * @return update trade page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        // get Trade by ID
        Optional<Trade> existingTrade = tradeService.getTradeById(id);
        // and to model then show to the form
        if (existingTrade.isPresent()) {
            model.addAttribute("trade", existingTrade.get());
            return "trade/update";
        }
        redirectAttributes.addFlashAttribute("error", "Provided trade with ID " + id + " does not exist.");
        return "redirect:/trade/list";
    }

    /**
     * Validates the fields in update trade form.
     *
     * @param id
     *         ID of trade to be updated.
     * @param trade
     *         updated fields of existing trade
     * @param result
     *         Result of validation
     *
     * @return update trade if successful, list of trades otherwise
     */
    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        // check required fields
        if (result.hasErrors()) return "trade/update";
        // if valid call service to update Trade
        trade.setTradeId(id);
        tradeService.updateTrade(trade);
        // and return Trade list
        redirectAttributes.addFlashAttribute("success", "Trade with ID " + id + " was successfully updated.");
        model.addAttribute("trades", tradeService.getTrades());
        return "redirect:/trade/list";
    }

    /**
     * Deletes a trade
     *
     * @param id
     *         ID of trade to be deleted
     *
     * @return list of trades page
     */
    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        // Find Trade by ID
        Optional<Trade> existingTrade = tradeService.getTradeById(id);
        // and delete the Trade
        if (existingTrade.isPresent()) {
            tradeService.deleteTrade(id);
            redirectAttributes.addFlashAttribute("success", "Trade with ID " + id + " was successfully deleted.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Provided trade with ID " + id + " does not exist.");
        }
        // return to Trade list
        model.addAttribute("trades", tradeService.getTrades());
        return "redirect:/trade/list";
    }
}
