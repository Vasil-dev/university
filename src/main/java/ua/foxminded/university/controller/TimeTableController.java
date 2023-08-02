package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import ua.foxminded.university.model.TimeTable;
import ua.foxminded.university.service.impl.TimeTableServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/schedule")
public class TimeTableController {

    private final TimeTableServiceImpl timeTableService;

    @Autowired
    public TimeTableController(TimeTableServiceImpl timeTableService) {
        this.timeTableService = timeTableService;
    }

    @GetMapping("/all")
    public String getAllSchedules(Model model) {
        List<TimeTable> timeTables = timeTableService.getAll();
        model.addAttribute("schedules", timeTables);

        return "schedule/SchedulePage";
    }

    @GetMapping("/new")
    public String showCreateScheduleForm() {
        return "schedule/CreateSchedule";
    }

    @PostMapping("/new/save")
    public String createNewSchedule(@Valid @ModelAttribute("schedule") TimeTable timeTable) {
        timeTableService.create(timeTable);
        return "redirect:/schedule/all";
    }

    @PostMapping("/{scheduleId}/delete")
    public String deleteSchedule(@PathVariable("scheduleId") int scheduleId) {
        TimeTable timeTable = timeTableService.getById(scheduleId);

        if (timeTable == null) {
            throw new IllegalArgumentException("Schedule with ID " + scheduleId + " not found.");
        } else {
            timeTableService.delete(scheduleId);
        }
        return "redirect:/schedule/all";
    }

    @PostMapping("/update/{scheduleId}")
    public String showUpdateScheduleView(@PathVariable("scheduleId") int scheduleId, Model model) {
        TimeTable timeTable = timeTableService.getById(scheduleId);

        if (timeTable == null) {
            throw new IllegalArgumentException("Schedule with ID " + scheduleId + " not found.");
        } else {
            model.addAttribute("schedule", timeTable);
            return "schedule/UpdateSchedule";
        }
    }

    @PostMapping("/update/save")
    public String updateSchedule(@Valid @ModelAttribute("schedule") TimeTable table) {
        TimeTable timeTable = timeTableService.getById(table.getId());

        if (timeTable != null) {
            timeTable.setStart(table.getStart());
            timeTable.setDuration(table.getDuration());
            timeTableService.update(timeTable);
        }
        return "redirect:/schedule/all";
    }
}