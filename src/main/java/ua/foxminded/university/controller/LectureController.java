package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.dto.LectureDto;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.service.impl.GroupServiceImpl;
import ua.foxminded.university.service.impl.LectureServiceImpl;
import ua.foxminded.university.service.impl.TeacherServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/lecture")
public class LectureController {

    private final LectureServiceImpl lectureService;
    private final GroupServiceImpl groupService;
    private final TeacherServiceImpl teacherService;

    @Autowired
    public LectureController(LectureServiceImpl lectureService, GroupServiceImpl groupService, TeacherServiceImpl teacherService) {
        this.lectureService = lectureService;
        this.groupService = groupService;
        this.teacherService = teacherService;
    }

    @GetMapping("/all")
    public String getAllLectures(Model model) {
        List<Lecture> lectures = lectureService.getAll();
        model.addAttribute("lectures", lectures);
        return "lecture/LecturePage";
    }

    @PostMapping("/{lectureId}/delete")
    public String deleteUser(@PathVariable("lectureId") int lectureId) {
        lectureService.delete(lectureId);
        return "redirect:/lecture/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String showCreateLecturePage() {
        return "lecture/CreateLecture";
    }

    @PostMapping("/new/save")
    public String saveNewLecture(@Valid @ModelAttribute("lecture") LectureDto lecture) {
        lectureService.create(lecture);
        return "redirect:/lecture/all";
    }

    @RequestMapping("/add/group")
    public String addGroupToLecturePage(Model model) {
        List<Lecture> lectures = lectureService.getAll();
        List<Group> groups = groupService.getAll();

        model.addAttribute("lectureList", lectures);
        model.addAttribute("groupList", groups);

        return "lecture/addGroup";
    }

    @PostMapping("add/group/save")
    public String saveGroupToLecture(@RequestParam("groupName") String groupName,
                                     @RequestParam("lectureName") String lectureName) {

        Lecture lecture = lectureService.getByName(lectureName);
        Group group = groupService.getByName(groupName);

        if (group != null && lecture != null) {
            lecture.setGroup(group);
            lectureService.update(lecture);
        }
        return "redirect:/lecture/all";
    }

    @RequestMapping("add/teacher")
    public String addTeacherPage(Model model) {
        List<Lecture> lectures = lectureService.getAll();
        List<Teacher> teachers = teacherService.getAll();

        model.addAttribute("lectureList", lectures);
        model.addAttribute("teachersList", teachers);

        return "lecture/addTeacher";
    }

    @PostMapping("add/teacher/save")
    public String saveTeacherToLecture(@RequestParam("lectureName") String lectureName,
                                       @RequestParam("teacherId") int teacherId) {

        Lecture lecture = lectureService.getByName(lectureName);
        Teacher teacher = teacherService.getById(teacherId);

        if (teacher != null && lecture != null) {
            lecture.setTeacher(teacher);
            lectureService.update(lecture);
        }

        return "redirect:/lecture/all";
    }

}
