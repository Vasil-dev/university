package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import ua.foxminded.university.dto.Registration;
import ua.foxminded.university.model.Role;
import ua.foxminded.university.model.UserEntity;
import ua.foxminded.university.service.impl.RoleServiceImpl;
import ua.foxminded.university.service.impl.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class AdminPanelController {
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @Autowired
    public AdminPanelController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public String manageUsers(Model model) {
        List<UserEntity> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/users";
    }

    @GetMapping("/add")
    public String addUser() {
        return "/users/add_user";
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

    @RequestMapping("/edit")
    public String assignRoleForm(Model model) {
        List<UserEntity> users = userService.getAllUsers();
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("usersList", users);
        model.addAttribute("rolesList", roles);

        return "users/get_role";
    }

    @PostMapping("/edit/save")
    public String assignRole(@RequestParam("userName") String userName, @RequestParam("roleName") String roleName) {
        UserEntity user = userService.getUserByUsername(userName);
        Role role = roleService.getRoleByName(roleName);

        if (user != null && role != null) {
            final List<Role> userRoles = user.getRoles();

            if (!userRoles.contains(role)) {
                userRoles.clear();
                userRoles.add(role);
                user.setRoles(userRoles);
                userService.updateUser(user);
            }
        }
        return "redirect:/users/all";
    }

    @PostMapping("/{userId}/delete")
    public String deleteUser(@PathVariable("userId") int userId) {
        userService.deleteUserById(userId);
        return "redirect:/users/all";
    }
}