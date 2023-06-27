package ua.foxminded.university.service.impl;

import org.springframework.stereotype.Service;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.repository.TeacherRepository;
import ua.foxminded.university.service.TeacherService;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }


    @Override
    public Teacher getById(long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @Override
    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher create(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher update(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public void delete(long id) {
        teacherRepository.deleteById(id);
    }
}
