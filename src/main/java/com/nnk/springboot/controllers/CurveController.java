package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
public class CurveController {

    @Autowired
    private CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        log.info("List curvePoints bids");
        List<CurvePoint> curvePoints = curvePointService.findAll();
        model.addAttribute("curvePoints", curvePoints);
        log.info("{} curvePoints retrieved", curvePoints.size());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        log.info("Display form to add a new curvePoint");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        log.info("Attempt to validate and save a new curvePoint");
        if (result.hasErrors()) {
            log.warn("Validation errors while submitting new curvePoint");
            return "curvePoint/add";
        }

        curvePointService.save(curvePoint);
        log.info("New curvePoint saved successfully: {}", curvePoint);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Display update form for curvePoint with id={}", id);
        model.addAttribute("curvePoint", curvePointService.findById(id));
        log.info("CurvePoint with id={} loaded for update", id);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        log.info("Attempt to update curvePoint with id={}", id);
        if (result.hasErrors()) {
            log.warn("Validation errors while updating curvePoint with id={}", id);
            return "curvePoint/update";
        }

        curvePointService.update(id, curvePoint);
        log.info("CurvePoint with id={} updated successfully", id);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        log.info("Attempt to delete curvePoint with id={}", id);
        CurvePoint curvePoint = curvePointService.findById(id);
        curvePointService.delete(curvePoint);
        log.info("CurvePoint with id={} deleted successfully", id);
        return "redirect:/curvePoint/list";
    }
}
