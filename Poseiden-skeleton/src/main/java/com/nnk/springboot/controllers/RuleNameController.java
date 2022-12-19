package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleService;
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
@RequestMapping("/ruleName")
public class RuleNameController {
    @Autowired
    private RuleService ruleService;

    /**
     * Returns list of rules.
     *
     * @param model
     *         holder for context data to be passed from controller to the view
     *
     * @return list of rules page
     */
    @RequestMapping("/list")
    public String home(Model model, Principal user) {
        // find all RuleName, add to model
        model.addAttribute("rules", ruleService.getRules());
        model.addAttribute("username", UserService.getUsername(user));
        return "ruleName/list";
    }

    /**
     * Shows page to add new rule.
     *
     * @param ruleName
     *         rule to create
     *
     * @return add rule page
     */
    @GetMapping("/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }

    /**
     * Validates the files in the add rule form.
     *
     * @param ruleName
     *         rule to add
     * @param result
     *         result of validation
     *
     * @return list of rules page
     */
    @PostMapping("/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, RedirectAttributes redirectAttributes) {
        // check data valid
        if (result.hasErrors()) {
            String error = "The form contains errors.";
            log.error(error);
            redirectAttributes.addFlashAttribute("error", error);
            return "ruleName/add";
        }
        // and save to db
        ruleService.saveRule(ruleName);
        redirectAttributes.addFlashAttribute("success", "Rule name was successfully created.");
        // after saving return Rating list
        return "redirect:/ruleName/list";
    }

    /**
     * Shows update rule form
     *
     * @param id
     *         ID of rule to update
     * @param model
     *         holder for context data to be passed from controller to the view
     *
     * @return update rule page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        // get Rating by ID
        Optional<RuleName> existingRule = ruleService.getRuleById(id);
        if (existingRule.isPresent()) {
            // and to model then show to the form
            model.addAttribute("ruleName", existingRule.get());
            return "ruleName/update";
        }
        redirectAttributes.addFlashAttribute("error", "Provided rule name with ID " + id + " does not exist.");
        return "redirect:/ruleName/list";
    }

    /**
     * Validates the fields in update rule form.
     *
     * @param id
     *         ID of rule to be updated.
     * @param ruleName
     *         updated fields of existing rule
     * @param result
     *         Result of validation
     *
     * @return update rule if successful, list of rules otherwise
     */
    @PostMapping("/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, RedirectAttributes redirectAttributes) {
        // check required fields
        if (result.hasErrors()) return "ruleName/update";
        // if valid call service to update RuleName
        ruleService.updateRule(ruleName);
        // and return RuleName list
        redirectAttributes.addFlashAttribute("success", "Rule name with ID " + id + " was successfully updated.");
        return "redirect:/ruleName/list";
    }

    /**
     * Deletes a rule
     *
     * @param id
     *         ID of rule to be deleted
     *
     * @return list of rules page
     */
    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        // Find ruleName by ID
        Optional<RuleName> existingRule = ruleService.getRuleById(id);
        // and delete the Rule
        if (existingRule.isPresent()) {
            ruleService.deleteRule(id);
            redirectAttributes.addFlashAttribute("success", "Rule with ID " + id + " was successfully deleted.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Provided rule with ID " + id + " does not exist.");
        }
        // return to Rating list
        return "redirect:/ruleName/list";
    }
}
