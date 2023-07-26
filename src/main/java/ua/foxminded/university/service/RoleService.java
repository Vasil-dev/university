package ua.foxminded.university.service;

import ua.foxminded.university.model.Role;

import java.util.List;

public interface RoleService {

    Role getRoleByName(String name);

    List<Role> getAllRoles();

    Role getRoleById(int id);
}
