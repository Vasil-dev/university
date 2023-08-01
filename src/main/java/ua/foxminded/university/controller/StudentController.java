package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.model.Student;
import ua.foxminded.university.service.impl.GroupServiceImpl;
import ua.foxminded.university.service.impl.StudentServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final StudentServiceImpl studentService;

    @Autowired
    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public String getAllStudents(Model model) {
        List<Student> students = studentService.getAll();
        model.addAttribute("students", students);
        return "student/StudentPage";
    }

    @RequestMapping("/new")
    public String showCreateStudentForm() {
        return "student/CreateStudent";
    }

    @PostMapping("/new/save")
    public String createStudent(@Valid @ModelAttribute("student") Student student) {
        studentService.create(student);
        return "redirect:/student/all";
    }

    @PostMapping("/{studentId}/delete")
    public String deleteStudent(@PathVariable("studentId") int studentId) {
        Student student = studentService.getById(studentId);

        if (student == null) {
            throw new IllegalArgumentException("Student with ID " + studentId + " not found.");
        } else {
            studentService.delete(studentId);
        }

        return "redirect:/student/all";
    }

    @PostMapping("/update/{studentId}")
    public String showRenameStudentView(@PathVariable("studentId") int studentId, Model model) {
        Student student = studentService.getById(studentId);

        if (student == null) {
            throw new IllegalArgumentException("Student with ID " + studentId + " not found.");
        } else {
            model.addAttribute("student", student);
            return "student/UpdateStudentRename";
        }
    }

    @PostMapping("/update/save")
    public String renameStudent(@Valid @ModelAttribute("student") Student updatedStudent) {
        Student student = studentService.getById(updatedStudent.getId());
        if (student != null) {
            student.setFirstName(updatedStudent.getFirstName());
            student.setLastName(updatedStudent.getLastName());
            studentService.update(student);
        }
        return "redirect:/student/all";
    }
}