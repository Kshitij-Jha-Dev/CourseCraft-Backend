package com.onlineCourse.eduhub.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HomeController {

	@GetMapping("/")
	public ResponseEntity<?> home() {
	    return ResponseEntity.ok(Map.of(
	            "success", true,
	            "message", "Welcome to EduHub - Your Gateway to Knowledge!"
	    ));
	}

	@GetMapping("/health")
	public ResponseEntity<?> health() {
	    return ResponseEntity.ok(Map.of(
	            "status", "UP"
	    ));
	}
}