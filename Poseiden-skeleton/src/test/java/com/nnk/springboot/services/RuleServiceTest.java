package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.ResourceNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@Import(RuleService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RuleServiceTest {
    /**
     * Class under test.
     */
    @Autowired
    RuleService service;

    @MockBean
    RuleNameRepository repository;

    private RuleName rule;

    @BeforeAll
    void init() {
        rule = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");
    }


    @Test
    public void testSaveRule() {
        when(repository.save(rule)).thenReturn(rule);
        service.saveRule(rule);
        verify(repository, times(1)).save(rule);
    }

    @Test
    public void testGetRuleById_successful() {
        when(repository.findById(1)).thenReturn(Optional.of(rule));
        assertEquals(Optional.of(rule), service.getRuleById(1));
    }

    @Test
    public void testGetRuleById_withNullId() {
        assertThrows(Exception.class, () -> service.getRuleById(null));
    }

    @Test
    public void testGetRules() {
        when(repository.findAll()).thenReturn(Collections.singletonList(rule));
        assertEquals(Collections.singletonList(rule), service.getRules());
    }

    @Test
    public void testUpdateRule_successful() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(rule));
        service.updateRule(rule);
        verify(repository, times(1)).save(rule);
    }

    @Test
    public void testUpdateRule_witIdNotPresent() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.updateRule(rule));
    }

    @Test
    public void testDeleteRule_successful() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(rule));
        service.deleteRule(rule.getId());
        verify(repository, times(1)).deleteById(rule.getId());
    }

    @Test
    public void testDeleteRule_witIdNotPresent() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deleteRule(1));
    }

    @Test
    public void testDeleteRule_withNullId() {
        assertThrows(Exception.class, () -> service.deleteRule(null));
    }
}