package com.nnk.springboot.controllers.rest;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.services.RuleService;
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
@RequestMapping("/ruleName")
public class RuleRestController {
    @Autowired
    private final RuleService service;

    private final String IDNotFoundMessage = "No rule found with id:";

    /**
     * Constructor.
     * @param service service layer
     */
    public RuleRestController(RuleService service) {this.service = service;}

    /**
     * Returns all rules.
     */
    @GetMapping
    public List<RuleName> getRules() {
        return service.getRules();
    }

    /**
     * Returns a rule if it exists.
     * @param id ID of rule to find
     * @return a rule or throws ResourceNotFoundException
     *
     * @see com.nnk.springboot.domain.RuleName
     * @see com.nnk.springboot.exceptions.ResourceNotFoundException
     */
    @GetMapping("/{id}")
    public RuleName getRuleByID(@PathVariable Integer id) {
        Assert.notNull(id, "ID must be provided.");
        return service.getRuleById(id)
                .orElseThrow(() -> new ResourceNotFoundException(IDNotFoundMessage + id +"."));
    }

    /**
     * Creates a rule.
     * @param rule Rule to create.
     * @return saved rule.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RuleName createRule(@RequestBody RuleName rule) {
        return service.saveRule(rule);
    }

    /**
     * Updates a rule.
     * @param rule Rule to update.
     * @return updated rule.
     */
    @PutMapping
    public RuleName updateRule(@RequestBody RuleName rule) {
        return service.updateRule(rule);
    }

    /**
     * Deletes a rule.
     *
     * @param id
     *         ID of rule to delete.
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRule(@PathVariable Integer id) {
        if (id == null) throw new ResourceNotFoundException(IDNotFoundMessage + id + ".");
        service.deleteRule(id);
    }
}
