package com.nnk.springboot.controllers;

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

import jakarta.validation.Valid;

import java.util.List;

@Slf4j
@Controller
public class BidListController {

    @Autowired
    private BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        log.info("List all bids");
        List<BidList> bids = bidListService.findAll();
        model.addAttribute("bidLists", bids);
        log.info("{} bids retrieved", bids.size());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        log.info("Display form to add a new bid");
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {

        log.info("Attempt to validate and save a new bid");
        if(result.hasErrors()){
            log.warn("Validation errors while submitting new bid");
            return "bidList/add";
        }

        bidListService.save(bid);
        log.info("New bid saved successfully: {}", bid);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        log.info("Display update form for bid with id={}", id);
        model.addAttribute("bidList", bidListService.findById(id));
        log.info("Bid with id={} loaded for update", id);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {

        log.info("Attempt to update bid with id={}", id);
        if (result.hasErrors()) {
            log.warn("Validation errors while updating bid with id={}", id);
            return "bidList/update";
        }

        bidListService.update(id, bidList);
        log.info("Bid with id={} updated successfully", id);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {

        log.info("Attempt to delete bid with id={}", id);
        BidList bid = bidListService.findById(id);
        bidListService.delete(bid);
        log.info("Bid with id={} deleted successfully", id);
        return "redirect:/bidList/list";
    }
}
