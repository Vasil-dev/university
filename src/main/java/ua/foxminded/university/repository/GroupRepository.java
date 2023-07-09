package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.university.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {
    Group findById(int id);
    Group findByName(String name);

}
