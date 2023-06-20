package ua.foxminded.university.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.service.impl.LectureServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LectureControllerTest {

    @Mock
    private LectureServiceImpl lectureService;

    @InjectMocks
    private LectureController lectureController;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllLectures() {
        List<Lecture> lectures = new ArrayList<>();
        lectures.add(new Lecture(1,"Some Lecture"));
        lectures.add(new Lecture(2,"Some Lecture"));
        when(lectureService.getAll()).thenReturn(lectures);

        String lectureFromController = lectureController.getAllLectures(model);
        assertEquals("lecture/LecturePage", lectureFromController);
        verify(model).addAttribute("lectures",lectures);
        verify(lectureService).getAll();
    }
}