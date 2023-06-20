package ua.foxminded.university.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import ua.foxminded.university.model.Student;
import ua.foxminded.university.service.impl.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentControllerTest {

    @Mock
    private StudentServiceImpl studentService;

    @InjectMocks
    private StudentController studentController;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1,"First Name","Last Name"));
        students.add(new Student(2,"First Name","Last Name"));
        students.add(new Student(3,"First Name","Last Name"));
        when(studentService.getAll()).thenReturn(students);

        String studentsFromController = studentController.getAllStudents(model);
        assertEquals("student/StudentPage", studentsFromController);
        verify(model).addAttribute("students", students);
        verify(studentService).getAll();
    }
}