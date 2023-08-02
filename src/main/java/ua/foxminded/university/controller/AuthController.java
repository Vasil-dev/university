package ua.foxminded.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}