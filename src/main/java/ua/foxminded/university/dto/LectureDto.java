package ua.foxminded.university.dto;

import lombok.Builder;
import lombok.Data;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.model.Student;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.model.TimeTable;

@Data
@Builder
public class LectureDto {
    private int id;
    private String lectureName;
    private Group group;
    private Teacher teacher;
    private Student student;
    private TimeTable timeTable;
}
