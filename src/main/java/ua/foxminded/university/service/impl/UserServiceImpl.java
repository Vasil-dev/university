package ua.foxminded.university.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.foxminded.university.dto.Registration;
import ua.foxminded.university.model.Role;
import ua.foxminded.university.model.UserEntity;
import ua.foxminded.university.repository.RoleRepository;
import ua.foxminded.university.repository.UserRepository;
import ua.foxminded.university.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final List<UserEntity> users;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.users = new ArrayList<>();
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(Registration registration) {
        UserEntity user = new UserEntity();
        user.setUserName(registration.getUserName());
        user.setEmail(registration.getEmail());
        user.setPassword((passwordEncoder.encode(registration.getPassword())));
        Role role = roleRepository.findByName("USER");
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        users.add(user);
        return user;
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        for (UserEntity user : users) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return users;
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteUser(String username) {
        UserEntity user = getUserByUsername(username);
        if (user != null) {
            users.remove(user);
        }
    }
}
