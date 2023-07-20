package ua.foxminded.university.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Data;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.model.Student;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.model.TimeTable;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class LectureDto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String lectureName;
    private Group group;
    private Teacher teacher;
    private Student student;
    private TimeTable timeTable;

    public LectureDto(Integer id, String lectureName, Group group, Teacher teacher, Student student, TimeTable timeTable) {
        this.id = id;
        this.lectureName = lectureName;
        this.group = group;
        this.teacher = teacher;
        this.student = student;
        this.timeTable = timeTable;
    }

    public LectureDto() {

    }
}
