package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
public class TradeController {
    @Autowired
    private TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        log.info("List all trades");
        List<Trade> trades = tradeService.findAll();
        model.addAttribute("trades", trades);
        log.info("{} trades retrieved", trades.size());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        log.info("Display form to add a new trade");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {

        log.info("Attempt to validate and save a new trade");
        if (result.hasErrors()) {
            log.warn("Validation errors while submitting new trade");
            return "trade/add";
        }

        tradeService.save(trade);
        log.info("New trade saved successfully: {}", trade);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        log.info("Display update form for trade with id={}", id);
        model.addAttribute("trade", tradeService.findById(id));
        log.info("Trade with id={} loaded for update", id);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        log.info("Attempt to update trade with id={}", id);
        if (result.hasErrors()) {
            log.warn("Validation errors while updating trade with id={}", id);
            return "trade/update";
        }

        tradeService.update(id, trade);
        log.info("Trade with id={} updated successfully", id);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        log.info("Attempt to delete trade with id={}", id);
        Trade trade = tradeService.findById(id);
        tradeService.delete(trade);
        log.info("Trade with id={} deleted successfully", id);
        return "redirect:/trade/list";
    }
}
