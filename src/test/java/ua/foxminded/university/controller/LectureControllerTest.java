package ua.foxminded.university.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.university.dto.LectureDto;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.model.UserEntity;
import ua.foxminded.university.service.impl.GroupServiceImpl;
import ua.foxminded.university.service.impl.LectureServiceImpl;
import ua.foxminded.university.service.impl.TeacherServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LectureController.class)
class LectureControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LectureServiceImpl lectureService;

    @MockBean
    private GroupServiceImpl groupService;

    @MockBean
    private TeacherServiceImpl teacherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        UserEntity user = new UserEntity();
        user.setUserName("admin");
        user.setPassword("admin");

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()));

    }

    @Test
    void testGetAllLectures() throws Exception {
        List<Lecture> lectures = Arrays.asList(
                new Lecture(1, "Some Lecture"),
                new Lecture(2, "Some Lecture"));

        when(lectureService.getAll()).thenReturn(lectures);

        mvc.perform(MockMvcRequestBuilders.get("/lecture/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("lecture/LecturePage"))
                .andExpect(model().attribute("lectures", lectures));
    }

    @Test
    void testDeleteLecture() throws Exception {

        doNothing().when(lectureService).delete(1);

        mvc.perform(MockMvcRequestBuilders.post("/lecture/{lectureId}/delete", 1)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/lecture/all"));

        verify(lectureService).delete(1);
    }

    @Test
    void testShowCreateLecturePage() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/lecture/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("lecture/CreateLecture"));
    }

    @Test
    void testSaveNewLecture() throws Exception {

        LectureDto lectureDto = new LectureDto();

        lectureDto.setId(1);
        lectureDto.setLectureName("Some Lecture");

        mvc.perform(MockMvcRequestBuilders.post("/lecture/new/save")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("id", "1")
                        .param("lectureName", "Some Lecture"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/lecture/all"));

        verify(lectureService).create(lectureDto);
    }

    @Test
    void testAddGroupToLecture() throws Exception {

        List<Lecture> lectures = Arrays.asList(
                new Lecture(1, "Lecture1"),
                new Lecture(2, "Lecture2"));

        List<Group> groups = Arrays.asList(
                new Group(1, "Group1"),
                new Group(2, "Group2"));

        when(lectureService.getAll()).thenReturn(lectures);
        when(groupService.getAll()).thenReturn(groups);

        mvc.perform(MockMvcRequestBuilders.get("/lecture/add/group"))
                .andExpect(status().isOk())
                .andExpect(view().name("lecture/addGroup"))
                .andExpect(model().attributeExists("lectureList", "groupList"))
                .andExpect(model().attribute("lectureList", lectures))
                .andExpect(model().attribute("groupList", groups));

        verify(lectureService, times(1)).getAll();
        verify(groupService, times(1)).getAll();
    }

    @Test
    void testSaveGroupToLecture() throws Exception {

        Lecture lecture = new Lecture(1, "Lecture Name");
        Group group = new Group(1, "Group Name");

        when(lectureService.getByName("Lecture Name")).thenReturn(lecture);
        when(groupService.getByName("Group Name")).thenReturn(group);

        mvc.perform(MockMvcRequestBuilders.post("/lecture/add/group/save")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("groupName", "Group Name")
                        .param("lectureName", "Lecture Name"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/lecture/all"));

        verify(lectureService, times(1)).getByName("Lecture Name");
        verify(groupService, times(1)).getByName("Group Name");
        verify(lectureService, times(1)).update(lecture);
    }

    @Test
    void testAddTeacherToLecture() throws Exception {

        List<Lecture> lectures = new ArrayList<>();
        lectures.add(new Lecture(1, "Lecture 1"));
        lectures.add(new Lecture(2, "Lecture 2"));

        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher(1, "First Name 1", "Last Name 1"));
        teachers.add(new Teacher(2, "First Name 2", "Last Name 2"));

        when(lectureService.getAll()).thenReturn(lectures);
        when(teacherService.getAll()).thenReturn(teachers);

        mvc.perform(MockMvcRequestBuilders.get("/lecture/add/teacher"))
                .andExpect(status().isOk())
                .andExpect(view().name("lecture/addTeacher"))
                .andExpect(model().attributeExists("lectureList", "teachersList"))
                .andExpect(model().attribute("lectureList", lectures))
                .andExpect(model().attribute("teachersList", teachers));

        verify(lectureService, times(1)).getAll();
        verify(teacherService, times(1)).getAll();
    }

    @Test
    void testSaveTeacherToLecture() throws Exception {

        Lecture lecture = new Lecture(1, "Lecture Name");
        Teacher teacher = new Teacher(1, "First Name", "Last Name");

        when(lectureService.getByName("Lecture Name")).thenReturn(lecture);
        when(teacherService.getById(1)).thenReturn(teacher);

        mvc.perform(MockMvcRequestBuilders.post("/lecture/add/teacher/save")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("lectureName", "Lecture Name")
                        .param("teacherId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/lecture/all"));

        verify(lectureService, times(1)).getByName("Lecture Name");
        verify(teacherService, times(1)).getById(1);
        verify(lectureService, times(1)).update(lecture);

    }

}