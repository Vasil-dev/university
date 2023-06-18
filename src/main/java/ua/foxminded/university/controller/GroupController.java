package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.service.impl.GroupServiceImpl;

import java.util.List;


@Controller
@RequestMapping("/groups")
public class GroupController {

    private final GroupServiceImpl groupService;

    @Autowired
    public GroupController(GroupServiceImpl groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public String getAllGroups(Model model) {
        List<Group> groups = groupService.getAll();
        model.addAttribute("groups", groups);
        return "groups/GroupsPage";
    }

}
