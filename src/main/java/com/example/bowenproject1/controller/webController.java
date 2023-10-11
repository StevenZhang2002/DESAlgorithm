package com.example.bowenproject1.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class webController {

    @GetMapping("/")
    public String errorPage(){
        return "demo";
    }
}
