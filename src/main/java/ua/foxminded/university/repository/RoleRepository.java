package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.university.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
