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
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.service.impl.TeacherServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TeacherServiceImpl teacherService;

    @MockBean
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTeachers() throws Exception {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher(1,"First Name","Last Name"));
        teachers.add(new Teacher(2,"First Name","Last Name"));
        when(teacherService.getAll()).thenReturn(teachers);

        mvc.perform(get("/teacher/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher/TeacherPage"))
                .andExpect(model().attribute("teachers", teachers));
    }
}