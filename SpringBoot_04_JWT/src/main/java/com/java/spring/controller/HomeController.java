package com.java.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController
{
	@GetMapping("/hello")
	ResponseEntity<String> hello() 
	{
	    return new ResponseEntity<>("Hello World!", HttpStatus.OK);
	}
}
