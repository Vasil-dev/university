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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.model.UserEntity;
import ua.foxminded.university.service.impl.GroupServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
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

    @Test
    void testShowUpdateGroupView() throws Exception {
        List<Group> groups = Arrays.asList(
                new Group(1, "Group 1"),
                new Group(2, "Group 2")
        );
        when(groupService.getAll()).thenReturn(groups);

        mvc.perform(get("/group/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("group/UpdateGroupChoose"))
                .andExpect(model().attribute("groups", groups));
    }

    @Test
    void testShowRenameGroupView() throws Exception {
        int groupId = 1;
        Group group = new Group(groupId, "Group 1");

        when(groupService.getById(groupId)).thenReturn(group);

        mvc.perform(MockMvcRequestBuilders.get("/group/update/{groupId}", groupId))
                .andExpect(status().isOk())
                .andExpect(view().name("group/UpdateGroupRename"))
                .andExpect(model().attribute("group", group));
    }

    @Test
    void testRenameGroup() throws Exception {
        int groupId = 1;
        String updatedGroupName = "Updated Group";
        Group group = new Group(groupId, "Group 1");

        when(groupService.getById(groupId)).thenReturn(group);

        mvc.perform(MockMvcRequestBuilders.post("/group/update/save")
                        .param("id", String.valueOf(groupId))
                        .param("name", updatedGroupName)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/group/all"));

        verify(groupService).update(group);
    }

    @Test
    void testDeleteGroup() throws Exception {

        doNothing().when(groupService).delete(1);

        mvc.perform(MockMvcRequestBuilders.post("/group/{groupId}/delete", 1)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/group/all"));

        verify(groupService).delete(1);
    }
}