package ua.foxminded.university.service;

import ua.foxminded.university.dto.Registration;
import ua.foxminded.university.model.UserEntity;

import java.util.List;

public interface UserService {
    void saveUser(Registration registration);

    UserEntity updateUser(UserEntity user);
    UserEntity getUserByUsername(String username);
    List<UserEntity> getAllUsers();

    UserEntity getUserById();

    UserEntity getUserById(int id);

    void deleteUser(String username);

    void deleteUserById(int id);
}
