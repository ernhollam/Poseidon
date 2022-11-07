package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RuleService {
    @Autowired
    final
    RuleNameRepository ruleNameRepository;

    public RuleService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    /**
     * Creates new rule.
     */
    public RuleName saveRule(final RuleName bidList) {
        return ruleNameRepository.save(bidList);
    }

    /**
     * Gets a rule.
     *
     * @param id
     *         ID of rule list to find
     *
     * @return Optional rule list object.
     */
    public Optional<RuleName> getRuleById(final Integer id) {
        Assert.notNull(id, "ID must not be null.");
        return ruleNameRepository.findById(id);
    }

    /**
     * Returns list of all rules.
     */
    public List<RuleName> getRules() {
        return ruleNameRepository.findAll();
    }

    /**
     * Updates a rule.
     *
     * @param bidList
     *         rule to update
     *
     * @return updated rule object.
     */
    public RuleName updateRule(final RuleName bidList) {
        Integer id = bidList.getId();
        if (ruleNameRepository.findById(id).isPresent()) {
            return ruleNameRepository.save(bidList);
        } else {
            log.error("Provided rule with ID " + id + "does not exist.");
            throw new ResourceNotFoundException("Provided rule with ID " + id + "does not exist.");
        }
    }

    /**
     * Deletes a rule.
     *
     * @param id
     *         ID of rule to delete.
     */
    public void deleteRule(final Integer id) {
        Assert.notNull(id, "ID must not be null.");
        if (ruleNameRepository.findById(id).isPresent()) {
            ruleNameRepository.deleteById(id);
            log.debug("Deleted rule with ID " + id + ".");
        } else {
            log.error("There is no such rule with ID " + id + ".");
            throw new ResourceNotFoundException("There is no such rule with ID " + id + ".");
        }
    }

}
