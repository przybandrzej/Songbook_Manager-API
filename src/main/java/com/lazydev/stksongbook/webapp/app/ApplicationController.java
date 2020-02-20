package com.lazydev.stksongbook.webapp.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {

    @RequestMapping("/contact")
    public String contact() {
        return "contact";
    }
}
