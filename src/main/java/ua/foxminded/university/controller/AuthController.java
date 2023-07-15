package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.foxminded.university.dto.Registration;
import ua.foxminded.university.model.UserEntity;
import ua.foxminded.university.service.impl.UserServiceImpl;

import javax.validation.Valid;

@Controller
public class AuthController {
    private final UserServiceImpl userService;

    @Autowired
    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login/login";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") Registration user, BindingResult result, Model model) {
        UserEntity existingUserUsername = userService.getUserByUsername(user.getUserName());
        if (existingUserUsername != null && existingUserUsername.getUserName() != null
                && !existingUserUsername.getUserName().isEmpty()) {
            return "redirect:/register?fail";
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "login/login";
        }

        userService.saveUser(user);
        return "redirect:/users/all";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

}