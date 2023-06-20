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
import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.service.impl.LectureServiceImpl;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LectureController.class)
class LectureControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LectureServiceImpl lectureService;

    @MockBean
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllLectures() throws Exception {
        List<Lecture> lectures = new ArrayList<>();
        lectures.add(new Lecture(1,"Some Lecture"));
        lectures.add(new Lecture(2,"Some Lecture"));
        when(lectureService.getAll()).thenReturn(lectures);

        mvc.perform(get("/lecture/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("lecture/LecturePage"))
                .andExpect(model().attribute("lectures", lectures));
    }
}