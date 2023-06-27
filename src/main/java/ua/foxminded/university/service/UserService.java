package ua.foxminded.university.service;

import ua.foxminded.university.dto.Registration;
import ua.foxminded.university.model.UserEntity;

import java.util.List;

public interface UserService {
    void saveUser(Registration registration);

    UserEntity createUser(UserEntity user);
    UserEntity getUserByUsername(String username);
    List<UserEntity> getAllUsers();

    UserEntity findByEmail(String email);
    void deleteUser(String username);
}
