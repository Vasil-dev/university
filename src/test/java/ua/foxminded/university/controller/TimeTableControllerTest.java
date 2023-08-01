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
import ua.foxminded.university.model.TimeTable;
import ua.foxminded.university.model.UserEntity;
import ua.foxminded.university.service.impl.TimeTableServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TimeTableController.class)
class TimeTableControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TimeTableServiceImpl timeTableService;

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
    void testGetAllSchedules() throws Exception {
        List<TimeTable> timeTables = Arrays.asList(
                new TimeTable(1, "07:00:00", "01:00:00"),
                new TimeTable(2, "08:00:00", "01:00:00"),
                new TimeTable(3, "09:00:00", "01:00:00")
        );

        when(timeTableService.getAll()).thenReturn(timeTables);

        mvc.perform(MockMvcRequestBuilders.get("/schedule/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("schedule/SchedulePage"))
                .andExpect(model().attribute("schedules", timeTables));
    }

    @Test
    void showCreateScheduleForm() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/schedule/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("schedule/CreateSchedule"));
    }

    @Test
    void testCreateNewSchedule() throws Exception {

        TimeTable timeTable = new TimeTable(10, "07:00:00", "01:00:00");

        mvc.perform(MockMvcRequestBuilders.post("/schedule/new/save")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("id", "10")
                        .param("start", "07:00:00")
                        .param("duration", "01:00:00"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedule/all"));

        verify(timeTableService).create(timeTable);
    }

    @Test
    void testDeleteSchedule() throws Exception {
        final int id = 1;
        TimeTable timeTable = new TimeTable(id, "07:00:00", "01:00:00");

        when(timeTableService.getById(id)).thenReturn(timeTable);
        doNothing().when(timeTableService).delete(id);

        mvc.perform(MockMvcRequestBuilders.post("/schedule/{scheduleId}/delete", id)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedule/all"));

        verify(timeTableService).delete(id);
    }

    @Test
    void testShowUpdateScheduleView() throws Exception {
        final int id = 1;

        TimeTable timeTable = new TimeTable(id, "07:00:00", "01:00:00");

        when(timeTableService.getById(id)).thenReturn(timeTable);

        mvc.perform(MockMvcRequestBuilders.post("/schedule/update/{scheduleId}", id)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("schedule/UpdateSchedule"))
                .andExpect(model().attribute("schedule", timeTable));
    }

    @Test
    void updateSchedule() throws Exception {
        int id = 1;
        String start = "07:00:00";
        String duration = "01:00:00";

        TimeTable timeTable = new TimeTable(id, start, duration);

        when(timeTableService.getById(id)).thenReturn(timeTable);

        mvc.perform(MockMvcRequestBuilders.post("/schedule/update/save")
                        .param("id", String.valueOf(id))
                        .param("start", start)
                        .param("duration", duration)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedule/all"));

        verify(timeTableService).update(timeTable);
    }
}