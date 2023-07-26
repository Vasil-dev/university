package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.university.model.TimeTable;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
}
