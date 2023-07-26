package ua.foxminded.university.dto;

import lombok.Builder;
import lombok.Data;
import ua.foxminded.university.model.Lecture;

import java.util.List;

@Data
@Builder
public class TeacherDto {
    private int id;
    private String firstName;
    private String lastName;
    private List<Lecture> lectures;
}
