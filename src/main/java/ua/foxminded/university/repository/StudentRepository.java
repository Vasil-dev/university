package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.university.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
