package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.service.impl.TeacherServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherServiceImpl teacherService;

    @Autowired
    public TeacherController(TeacherServiceImpl teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/all")
    public String getAllTeachers(Model model) {
        List<Teacher> teachers = teacherService.getAll();
        model.addAttribute("teachers", teachers);
        return "teacher/TeacherPage";
    }
}
