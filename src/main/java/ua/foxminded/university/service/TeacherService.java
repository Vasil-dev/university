package ua.foxminded.university.service;

import ua.foxminded.university.model.Teacher;

import java.util.List;

public interface TeacherService {

    Teacher getById(long id);

    List<Teacher> getAll();

    Teacher create(Teacher teacher);

    Teacher update(Teacher teacher);

    void delete(long id);
}
