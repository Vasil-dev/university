package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.service.impl.LectureServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/lecture")
public class LectureController {

    private final LectureServiceImpl lectureService;

    @Autowired
    public LectureController(LectureServiceImpl lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping("/all")
    public String getAllLectures(Model model) {
        List<Lecture> lectures = lectureService.getAll();
        model.addAttribute("lectures", lectures);
        return "lecture/LecturePage";
    }
}
