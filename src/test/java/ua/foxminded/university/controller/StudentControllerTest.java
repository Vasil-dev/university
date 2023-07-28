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
import ua.foxminded.university.model.UserEntity;
import ua.foxminded.university.service.impl.StudentServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentServiceImpl studentService;

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
    void testGetAllStudents() throws Exception {
        List<Student> students = Arrays.asList(
                new Student(1, "First Name", "Last Name"),
                new Student(2, "First Name", "Last Name"),
                new Student(3, "First Name", "Last Name"));

        when(studentService.getAll()).thenReturn(students);

        mvc.perform(get("/student/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/StudentPage"))
                .andExpect(model().attribute("students", students));
    }

    @Test
    void testDeleteStudent() throws Exception {
        int studentId = 1;
        Student student = new Student(studentId, "First Name", "Last Name");

        when(studentService.getById(studentId)).thenReturn(student);
        doNothing().when(studentService).delete(studentId);
        mvc.perform(MockMvcRequestBuilders.post("/student/{studentId}/delete", studentId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/student/all"));

        verify(studentService).delete(studentId);
    }

    @Test
    void testShowUpdateStudent() throws Exception {
        List<Student> students = Arrays.asList(
                new Student(1, "First Name", "Last Name"),
                new Student(2, "First Name2", "Last Name2"));

        when(studentService.getAll()).thenReturn(students);

        mvc.perform(MockMvcRequestBuilders.get("/student/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/UpdateStudentChoose"));
    }

    @Test
    void testShowRenameStudent() throws Exception {
        int studentId = 1;
        Student student = new Student(studentId, "First Name", "Last Name");

        when(studentService.getById(studentId)).thenReturn(student);

        mvc.perform(MockMvcRequestBuilders.get("/student/update/{studentId}", studentId))
                .andExpect(status().isOk())
                .andExpect(view().name("student/UpdateStudentRename"))
                .andExpect(model().attribute("student", student));
    }

    @Test
    void testRenameStudent() throws Exception {
        int studentId = 1;
        String firstName = "Some First Name";
        String lastName = "Some Last Name";

        Student student = new Student(studentId,firstName, lastName);

        when(studentService.getById(studentId)).thenReturn(student);

        mvc.perform(MockMvcRequestBuilders.post("/student/update/save")
                .param("id", String.valueOf(studentId))
                .param("firstName", firstName)
                .param("lastName", lastName)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/student/all"));

        verify(studentService).update(student);

    }
}