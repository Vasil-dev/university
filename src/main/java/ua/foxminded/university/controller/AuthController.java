package ua.foxminded.university.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.foxminded.university.dto.Registration;
import ua.foxminded.university.model.UserEntity;
import ua.foxminded.university.service.UserService;

import javax.validation.Valid;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login/login";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") Registration user, BindingResult result, Model model) {
        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
        if (existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
            return "redirect:/register?fail";
        }

        UserEntity existingUserUsername = userService.getUserByUsername(user.getUserName());
        if (existingUserUsername != null && existingUserUsername.getUserName() != null
                && !existingUserUsername.getUserName().isEmpty()) {
            return "redirect:/register?fail";
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "login/register";
        }

        userService.saveUser(user);
        return "redirect:/users/all";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        return "redirect:/login";
    }

}