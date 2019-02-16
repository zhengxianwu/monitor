package com.monitor.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
	@RequestMapping("/hello")
	public String hello(Model model,
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		model.addAttribute("name", name);
		return "hello";
	}
	
	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}
	
	
	@RequestMapping("/cpu")
	public String cpu(Model model,@RequestParam(value="name",required=false,defaultValue="zhansang") String name) {
		model.addAttribute("name", name);
		return "cpu";
	}
	@RequestMapping("/memory")
	public String memory(Model mode) {
		return "memory";
	}
	@RequestMapping("/process")
	public String process(Model mode) {
		return "Process";
	}



}
