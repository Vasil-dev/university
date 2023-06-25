package ua.foxminded.university.dto;

import lombok.Builder;
import lombok.Data;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.model.Lecture;

import java.util.List;

@Data
@Builder
public class StudentDto {
    private long id;
    private String firstName;
    private String lastName;
    private Group group;
    private List<Lecture> lectures;
}
