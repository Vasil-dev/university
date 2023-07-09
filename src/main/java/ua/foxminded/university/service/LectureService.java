package ua.foxminded.university.service;

import ua.foxminded.university.dto.LectureDto;
import ua.foxminded.university.model.Lecture;

import java.util.List;

public interface LectureService {

    Lecture getByName(String name);
    Lecture getById(long id);

    List<Lecture> getAll();

    Lecture create(LectureDto lectureDto);

    Lecture update(Lecture lecture);

    void delete(long id);
}
