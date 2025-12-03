package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user/list")
    public String home(Model model)
    {
        log.info("List all users");
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        log.info("{} users retrieved", users.size());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User bid) {
        log.info("Display form to add a new user");
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        log.info("Attempt to validate and save a new user");
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            model.addAttribute("users", userRepository.findAll());
            log.info("New user saved successfully: {}", user);
            return "redirect:/user/list";
        }
        log.warn("Validation errors while submitting new user");
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Display update form for user with id={}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        log.info("User with id={} loaded for update", id);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        log.info("Attempt to update user with id={}", id);
        if (result.hasErrors()) {
            log.warn("Validation errors while updating user with id={}", id);
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        log.info("User with id={} updated successfully", id);
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        log.info("Attempt to delete user with id={}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        log.info("User with id={} deleted successfully", id);
        return "redirect:/user/list";
    }
}
