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
import org.springframework.ui.Model;
import ua.foxminded.university.model.Student;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.model.UserEntity;
import ua.foxminded.university.service.impl.TeacherServiceImpl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TeacherServiceImpl teacherService;

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
    void testGetAllTeachers() throws Exception {
        List<Teacher> teachers = Arrays.asList(
                new Teacher(1, "First Name", "Last Name"),
                new Teacher(2, "First Name", "Last Name"));

        when(teacherService.getAll()).thenReturn(teachers);

        mvc.perform(get("/teacher/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher/TeacherPage"))
                .andExpect(model().attribute("teachers", teachers));
    }

    @Test
    void testDeleteTeacher() throws Exception {
        int teacherId = 1;
        Teacher teacher = new Teacher(teacherId, "First Name", "Last Name");

        when(teacherService.getById(teacherId)).thenReturn(teacher);
        doNothing().when(teacherService).delete(teacherId);

        mvc.perform(MockMvcRequestBuilders.post("/teacher/{teacherId}/delete", teacherId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teacher/all"));

        verify(teacherService).delete(teacherId);
    }

    @Test
    void testShowRenameTeacher() throws Exception {
        int teacherId = 1;
        Teacher teacher = new Teacher(teacherId, "First Name", "Last Name");

        when(teacherService.getById(teacherId)).thenReturn(teacher);

        mvc.perform(MockMvcRequestBuilders.post("/teacher/update/{teacherId}", teacherId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher/UpdateTeacherRename"))
                .andExpect(model().attribute("teacher", teacher));
    }

    @Test
    void testRenameTeacher() throws Exception {
        int teacherId = 1;
        String firstName = "Some First Name";
        String lastName = "Some Last Name";

        Teacher teacher = new Teacher(teacherId, firstName, lastName);

        when(teacherService.getById(teacherId)).thenReturn(teacher);

        mvc.perform(MockMvcRequestBuilders.post("/teacher/update/save")
                        .param("id", String.valueOf(teacherId))
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teacher/all"));

        verify(teacherService).update(teacher);
    }
}