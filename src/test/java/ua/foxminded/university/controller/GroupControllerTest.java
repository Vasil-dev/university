package ua.foxminded.university.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.model.UserEntity;
import ua.foxminded.university.service.impl.GroupServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        UserEntity user = new UserEntity();
        user.setUserName("admin");
        user.setPassword("admin");

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()));

    }

    @Test
    void testGetAllGroups() throws Exception {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group(1, "Group A"));
        groups.add(new Group(2, "Group B"));
        when(groupService.getAll()).thenReturn(groups);

        mvc.perform(get("/group/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("group/GroupPage"))
                .andExpect(model().attribute("groups", groups));
    }

    @Test
    void testShowCreateGroupForm() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/group/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("group/CreateGroup"));
    }

    @Test
    void testCreateGroup() throws Exception {

        Group group = new Group(1, "Some Group");

        mvc.perform(MockMvcRequestBuilders.post("/group/new/save")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("id", "1")
                .param("name", "Some Group"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/group/all"));

        verify(groupService).create(group);
    }
}