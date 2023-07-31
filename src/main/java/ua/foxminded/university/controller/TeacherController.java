package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.service.impl.TeacherServiceImpl;

import javax.validation.Valid;
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

    @GetMapping("/new")
    public String showCreateTeacherForm() {
        return "teacher/CreateTeacher";
    }

    @PostMapping("/new/save")
    public String createTeacher(@Valid @ModelAttribute("student") Teacher teacher) {
        teacherService.create(teacher);
        return "redirect:/teacher/all";
    }

    @PostMapping("/{teacherId}/delete")
    public String deleteTeacher(@PathVariable("teacherId") int teacherId) {
        Teacher teacher = teacherService.getById(teacherId);

        if (teacher == null) {
            throw new IllegalArgumentException("Teacher with ID " + teacherId + " not found.");
        } else {
            teacherService.delete(teacherId);
        }

        return "redirect:/teacher/all";
    }

    @PostMapping("/update/{teacherId}")
    public String showRenameTeacherView(@PathVariable("teacherId") int teacherId, Model model) {
        Teacher teacher = teacherService.getById(teacherId);

        if (teacher == null) {
            throw new IllegalArgumentException("Teacher with ID " + teacherId + " not found.");
        } else {
            model.addAttribute("teacher", teacher);
            return "teacher/UpdateTeacherRename";
        }
    }

    @PostMapping("/update/save")
    public String renameTeacher(@Valid @ModelAttribute("teacher") Teacher updatedTeacher) {
        Teacher teacher = teacherService.getById(updatedTeacher.getId());
        if (teacher != null) {
            teacher.setFirstName(updatedTeacher.getFirstName());
            teacher.setLastName(updatedTeacher.getLastName());
            teacherService.update(teacher);
        }
        return "redirect:/teacher/all";
    }
}
