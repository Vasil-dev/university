package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.model.Role;
import ua.foxminded.university.model.UserEntity;
import ua.foxminded.university.service.impl.RoleServiceImpl;
import ua.foxminded.university.service.impl.UserServiceImpl;

import java.util.Collections;
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
            user.setRoles(Collections.singletonList(role));
            userService.createUser(user);
        }
        return "redirect:/users/all";
    }


    @RequestMapping("/delete/page")
    public String showDeleteUserForm( Model model) {
        List<UserEntity> user = userService.getAllUsers();
        model.addAttribute("user", user);
        return "users/delete";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("userName") String userName) {
        userService.getUserByUsername(userName);
        userService.deleteUser(userName);
        return "redirect:/users/all";
    }
}
