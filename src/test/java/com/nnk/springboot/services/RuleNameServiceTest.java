package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService;

    @Test
    void testFindAll() {
        when(ruleNameRepository.findAll()).thenReturn(List.of(new RuleName()));

        List<RuleName> result = ruleNameService.findAll();

        assertEquals(1, result.size());
        verify(ruleNameRepository).findAll();
    }

    @Test
    void testSave() {
        RuleName ruleName = new RuleName();
        ruleName.setName("Rule Test");

        when(ruleNameRepository.save(any())).thenReturn(ruleName);

        RuleName result = ruleNameService.save(ruleName);

        assertNotNull(result);
        assertEquals("Rule Test", result.getName());
        verify(ruleNameRepository).save(ruleName);
    }

    @Test
    void testFindById() {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Rule Test");

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        RuleName result = ruleNameService.findById(1);

        assertEquals("Rule Test", result.getName());
        verify(ruleNameRepository).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> ruleNameService.findById(1));
    }

    @Test
    void testUpdate() {
        RuleName existing = new RuleName();
        existing.setId(1);
        existing.setName("Rule Test");

        RuleName updated = new RuleName();
        updated.setName("New Rule Test");

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(existing));
        when(ruleNameRepository.save(any())).thenReturn(existing);

        RuleName result = ruleNameService.update(1, updated);

        assertEquals("New Rule Test", result.getName());
        verify(ruleNameRepository).save(existing);
    }

    @Test
    void testDelete() {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);

        ruleNameService.delete(ruleName);

        verify(ruleNameRepository).delete(ruleName);
    }
}

