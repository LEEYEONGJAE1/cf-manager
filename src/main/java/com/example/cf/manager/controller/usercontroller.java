package com.example.cf.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class usercontroller {
    @GetMapping("/userpage")
    public String user(){
        return "userpage";
    }

    @GetMapping("/question")
    public String question(){
        return "question";
    }
}
