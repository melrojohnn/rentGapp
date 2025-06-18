package com.melro.rentapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Frontend Controller
 *
 * This controller handles the main frontend routes and serves the HTML pages
 * for the rental application user interface.
 */
@Controller
public class FrontendController {

    /**
     * Main application page
     * Serves the main rental application interface
     *
     * @return the main application page
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Customers page
     * Serves the customers management interface
     *
     * @return the customers page
     */
    @GetMapping("/customers")
    public String customers() {
        return "customers";
    }
}
