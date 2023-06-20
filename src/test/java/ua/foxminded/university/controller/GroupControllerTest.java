package ua.foxminded.university.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.service.impl.GroupServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupController.class)
class GroupControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GroupServiceImpl groupService;

    @MockBean
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllGroups() throws Exception {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group(1,"Group A"));
        groups.add(new Group(2,"Group B"));
        when(groupService.getAll()).thenReturn(groups);

        mvc.perform(get("/group/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("group/GroupPage"))
                .andExpect(model().attribute("groups", groups));

    }
}