package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.service.impl.GroupServiceImpl;

import javax.validation.Valid;
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

    @RequestMapping("/new")
    public String showCreateGroupForm() {
        return "group/CreateGroup";
    }

    @PostMapping("/new/save")
    public String createGroup(@Valid @ModelAttribute("group") Group group) {
        groupService.create(group);
        return "redirect:/group/all";
    }

    @PostMapping("/{groupId}/delete")
    public String deleteGroup(@PathVariable("groupId") int groupId) {
        groupService.delete(groupId);
        return "redirect:/group/all";
    }

    @PostMapping("/update/{groupId}")
    public String showRenameGroupView(@PathVariable("groupId") int groupId, Model model) {
        Group group = groupService.getById(groupId);
        model.addAttribute("group", group);
        return "group/UpdateGroupRename";
    }

    @PostMapping("/update/save")
    public String renameGroup(@Valid @ModelAttribute("group") Group updatedGroup) {
        Group group = groupService.getById(updatedGroup.getId());
        if (group != null) {
            group.setName(updatedGroup.getName());
            groupService.update(group);
        }
        return "redirect:/group/all";
    }
}