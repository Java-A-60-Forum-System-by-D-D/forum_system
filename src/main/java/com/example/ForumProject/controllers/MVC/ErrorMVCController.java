package com.example.ForumProject.controllers.MVC;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/errors")
public class ErrorMVCController {
    @GetMapping("/unauthorized")
    public String getUnauthorizedPage() {
        return "errors/Unauthorized";
    }
}
