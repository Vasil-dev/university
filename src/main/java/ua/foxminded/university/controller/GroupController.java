package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.dto.GroupDto;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.service.impl.GroupServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/group")
public class GroupController {

    private final GroupServiceImpl groupService;

    @Autowired
    public GroupController(GroupServiceImpl groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/all")
    public String getAllGroups(Model model) {
        List<Group> groups = groupService.getAll();
        model.addAttribute("groups", groups);
        return "group/GroupPage";
    }

    @GetMapping("/new")
    public String showCreateGroupForm(Model model) {
        Group group = new Group();
        model.addAttribute("group", group);
        return "group/CreateGroup";
    }

    @PostMapping("/new")
    public String createGroup(@ModelAttribute("group") Group group) {
        groupService.create(group);
        return "redirect:/group/all";
    }
}
