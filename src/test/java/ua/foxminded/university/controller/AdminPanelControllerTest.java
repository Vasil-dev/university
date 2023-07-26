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
import ua.foxminded.university.dto.Registration;
import ua.foxminded.university.model.Role;
import ua.foxminded.university.model.UserEntity;
import ua.foxminded.university.service.impl.UserServiceImpl;
import ua.foxminded.university.service.impl.RoleServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminPanelController.class)
class AdminPanelControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private RoleServiceImpl roleService;

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
    void testManageUsers() throws Exception {

        UserEntity user = new UserEntity();
        user.setUserName("user");
        user.setPassword("user");

        List<UserEntity> users = new ArrayList<>();
        users.add(user);

        when(userService.getAllUsers()).thenReturn(users);

        mvc.perform(MockMvcRequestBuilders.get("/users/all")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("users/users"))
                .andExpect(model().attribute("users", users));
    }

    @Test
    void testAddUser() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/users/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("/users/add_user"));
    }

    @Test
    void testRegister_SuccessfulRegistration() throws Exception {
        Registration registration = new Registration();

        when(userService.getUserByUsername(registration.getUserName())).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/users/register/save")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("user", registration))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/all"));

        verify(userService, times(1)).saveUser(registration);
    }

    @Test
    void testAssignRoleForm() throws Exception {

        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1, "USER"));
        roles.add(new Role(1, "ADMIN"));

        List<UserEntity> users = new ArrayList<>();
        users.add(new UserEntity("user1", "password1"));
        users.add(new UserEntity("user2", "password2"));

        when(roleService.getAllRoles()).thenReturn(roles);
        when(userService.getAllUsers()).thenReturn(users);

        mvc.perform(MockMvcRequestBuilders.get("/users/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/get_role"))
                .andExpect(model().attributeExists("usersList", "rolesList"))
                .andExpect(model().attribute("usersList", users))
                .andExpect(model().attribute("rolesList", roles));

        verify(userService, times(1)).getAllUsers();
        verify(roleService, times(1)).getAllRoles();
    }

    @Test
    void testAssignRoleToUser() throws Exception {
        UserEntity user = new UserEntity("user", "password");
        Role role = new Role(1, "ADMIN");

        when(userService.getUserByUsername("user")).thenReturn(user);
        when(roleService.getRoleByName("ADMIN")).thenReturn(role);

        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        userService.updateUser(user);

        mvc.perform(MockMvcRequestBuilders.post("/users/edit/save")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("userName", "user")
                        .param("roleName", "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/all"));

        verify(userService, times(1)).getUserByUsername("user");
        verify(roleService, times(1)).getRoleByName("ADMIN");
        verify(userService, times(1)).updateUser(user);
    }

    @Test
    void testDeleteUser() throws Exception {

        doNothing().when(userService).deleteUserById(1);

        mvc.perform(MockMvcRequestBuilders.post("/users/{userId}/delete", 1)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/all"));

        verify(userService, times(1)).deleteUserById(1);
    }
}