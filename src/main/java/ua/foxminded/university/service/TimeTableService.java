package ua.foxminded.university.service;

import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.model.TimeTable;

import java.util.List;

public interface TimeTableService {

    TimeTable getById(long id);

    List<TimeTable> getAll();

    TimeTable create(TimeTable timeTable);

    TimeTable update(TimeTable timeTable);

    void delete(long id);
}
