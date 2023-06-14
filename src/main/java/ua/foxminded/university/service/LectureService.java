package ua.foxminded.university.service;

import ua.foxminded.university.model.Lecture;

import java.util.List;

public interface LectureService {

    Lecture getById(long id);

    List<Lecture> getAll();

    Lecture create(Lecture lecture);

    Lecture update(Lecture lecture);

    void delete(long id);
}
