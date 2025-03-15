package com.dairyFarm.dairyFarm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaEndPoint {
	
	@GetMapping("/Hola")
	public String TestAPI() {
		return "User Service is Up and Running on Port 8081";
	}

}
