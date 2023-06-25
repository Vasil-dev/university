package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.university.model.UserEntity;

public interface UserRepository  extends JpaRepository<UserEntity,Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserName(String userName);
    UserEntity findFirstByUserName(String userName);
}
