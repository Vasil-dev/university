package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.university.model.Lecture;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Lecture findByLectureName(String name);
}
