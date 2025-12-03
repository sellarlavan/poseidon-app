package com.nnk.springboot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class HomeController
{
	@RequestMapping("/")
	public String home(Model model)
	{
		return "redirect:/login";
	}

}
