package ua.foxminded.university.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.service.impl.TeacherServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TeacherControllerTest {

    @Mock
    private TeacherServiceImpl teacherService;

    @InjectMocks
    private TeacherController teacherController;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher(1,"First Name","Last Name"));
        teachers.add(new Teacher(2,"First Name","Last Name"));
        when(teacherService.getAll()).thenReturn(teachers);

        String teachersFromController = teacherController.getAllTeachers(model);
        assertEquals("teacher/TeacherPage", teachersFromController);
        verify(model).addAttribute("teachers", teachers);
        verify(teacherService).getAll();
    }
}