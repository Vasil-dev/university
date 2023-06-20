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
import ua.foxminded.university.model.Student;
import ua.foxminded.university.service.impl.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentServiceImpl studentService;

    @MockBean
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStudents() throws Exception {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1,"First Name","Last Name"));
        students.add(new Student(2,"First Name","Last Name"));
        students.add(new Student(3,"First Name","Last Name"));
        when(studentService.getAll()).thenReturn(students);

        mvc.perform(get("/student/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/StudentPage"))
                .andExpect(model().attribute("students", students));
    }
}