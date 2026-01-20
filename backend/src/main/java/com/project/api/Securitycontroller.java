package com.project.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Securitycontroller {

	@GetMapping("/hello")
	public String hello() {
		return "Spring Security !!";
	}
}
