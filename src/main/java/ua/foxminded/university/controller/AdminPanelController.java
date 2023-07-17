package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.model.Role;
import ua.foxminded.university.model.UserEntity;
import ua.foxminded.university.service.impl.RoleServiceImpl;
import ua.foxminded.university.service.impl.UserServiceImpl;

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
    @PreAuthorize("hasAuthority('ADMIN')")
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
