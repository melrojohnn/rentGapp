package com.melro.rentapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class HealthCheckController {

    private static final Logger logger = LoggerFactory.getLogger(HealthCheckController.class);

    @GetMapping
    public Map<String, Object> index() {
        logger.info("Health check endpoint called");

        Map<String, Object> response = new HashMap<>();
        try {
            response.put("status", "OK");
            response.put("message", "Rent app is running.");
            response.put("timestamp", Instant.now());
            response.put("app", "RentApp API");

            return response;

        } catch (Exception e) {
            logger.error("Health check failed", e);

            response.put("status", "ERROR");
            response.put("message", "Unexpected error occurred.");
            response.put("timestamp", Instant.now());

            return response;
        }
    }
}
