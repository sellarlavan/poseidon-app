package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.util.List;

@Slf4j
@Controller
public class RuleNameController {
    @Autowired
    private RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        log.info("List all ruleNames");
        List<RuleName> ruleNames = ruleNameService.findAll();
        model.addAttribute("ruleNames", ruleNameService.findAll());
        log.info("{} ruleNames retrieved", ruleNames.size());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {

        log.info("Display form to add a new ruleName");
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {

        log.info("Attempt to validate and save a new ruleName");
        if (result.hasErrors()) {
            log.warn("Validation errors while submitting new ruleName");
            return "ruleName/add";
        }

        ruleNameService.save(ruleName);
        log.info("New ruleName saved successfully: {}", ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        log.info("Display update form for ruleName with id={}", id);
        model.addAttribute("ruleName", ruleNameService.findById(id));
        log.info("RuleName with id={} loaded for update", id);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        log.info("Attempt to update ruleName with id={}", id);
        if (result.hasErrors()) {
            log.warn("Validation errors while updating ruleName with id={}", id);
            return "ruleName/update";
        }

        ruleNameService.update(id, ruleName);
        log.info("RuleName with id={} updated successfully", id);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {

        log.info("Attempt to delete ruleName with id={}", id);
        RuleName ruleName = ruleNameService.findById(id);
        ruleNameService.delete(ruleName);
        log.info("RuleName with id={} deleted successfully", id);
        return "redirect:/ruleName/list";
    }
}
