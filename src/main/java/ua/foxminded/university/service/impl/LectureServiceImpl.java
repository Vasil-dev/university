package ua.foxminded.university.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.repository.LectureRepository;
import ua.foxminded.university.service.LectureService;

import java.util.List;

@Service
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;

    @Autowired
    public LectureServiceImpl(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @Override
    public Lecture getById(long id) {
        return lectureRepository.findById(id).orElse(null);
    }

    @Override
    public List<Lecture> getAll() {
        return lectureRepository.findAll();
    }

    @Override
    public Lecture create(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    @Override
    public Lecture update(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    @Override
    public void delete(long id) {
        lectureRepository.deleteById(id);
    }
}
