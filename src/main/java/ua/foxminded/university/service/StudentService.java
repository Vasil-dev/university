package ua.foxminded.university.service;

import ua.foxminded.university.model.Student;

import java.util.List;

public interface StudentService {

    Student getById(long id);

    List<Student> getAll();

    Student create(Student student);

    Student update(Student student);

    void delete(long id);
}
