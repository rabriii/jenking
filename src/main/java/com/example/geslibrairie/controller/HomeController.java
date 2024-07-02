package com.example.geslibrairie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // Cela fait référence à /WEB-INF/jsp/index.jsp
    }
}
