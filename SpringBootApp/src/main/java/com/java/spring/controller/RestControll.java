package com.java.spring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControll
{

	@PostMapping(value="/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean getUser(@RequestParam String username,
			@RequestParam String password)
	{
		return (validate(username, password));
	}

	private Boolean validate(String username, String password)
	{
		return (username.equalsIgnoreCase("user1") && password.equals("pass"));
	}
}
