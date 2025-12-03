package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    public RuleName save(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    public RuleName findById(Integer id) {
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid RuleName Id: " + id));
    }

    public RuleName update(Integer id, RuleName updatedRule) {
        RuleName existing = findById(id);

        existing.setName(updatedRule.getName());
        existing.setDescription(updatedRule.getDescription());
        existing.setJson(updatedRule.getJson());
        existing.setTemplate(updatedRule.getTemplate());
        existing.setSqlStr(updatedRule.getSqlStr());
        existing.setSqlPart(updatedRule.getSqlPart());

        return ruleNameRepository.save(existing);
    }

    public void delete(RuleName ruleName) {
        ruleNameRepository.delete(ruleName);
    }
}
