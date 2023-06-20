package ua.foxminded.university.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.service.impl.GroupServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GroupControllerTest {

    @Mock
    private GroupServiceImpl groupService;

    @InjectMocks
    private GroupController groupController;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllGroups() {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group(1,"Group A"));
        groups.add(new Group(2,"Group B"));
        when(groupService.getAll()).thenReturn(groups);

        String groupsFromController = groupController.getAllGroups(model);
        assertEquals("group/GroupPage",groupsFromController);
        verify(model).addAttribute("groups", groups);
        verify(groupService).getAll();

    }
}