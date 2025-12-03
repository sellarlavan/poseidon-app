package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        log.info("List all ratings");
        List<Rating> ratings = ratingService.findAll();
        model.addAttribute("ratings", ratings);
        log.info("{} ratings retrieved", ratings.size());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        log.info("Display form to add a new rating");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        log.info("Attempt to validate and save a new rating");
        if (result.hasErrors()) {
            log.warn("Validation errors while submitting new rating");
            return "rating/add";
        }
        ratingService.save(rating);
        log.info("New rating saved successfully: {}", rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        log.info("Display update form for rating with id={}", id);
        model.addAttribute("rating", ratingService.findById(id));
        log.info("Rating with id={} loaded for update", id);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        log.info("Attempt to update rating with id={}", id);
        if (result.hasErrors()) {
            log.warn("Validation errors while updating rating with id={}", id);
            return "rating/update";
        }

        ratingService.update(id, rating);
        log.info("Rating with id={} updated successfully", id);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {

        log.info("Attempt to delete rating with id={}", id);
        Rating rating = ratingService.findById(id);
        ratingService.delete(rating);
        log.info("Rating with id={} deleted successfully", id);
        return "redirect:/rating/list";
    }
}
