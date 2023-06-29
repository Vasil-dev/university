package ua.foxminded.university.service.impl;

import org.springframework.stereotype.Service;
import ua.foxminded.university.model.Role;
import ua.foxminded.university.repository.RoleRepository;
import ua.foxminded.university.service.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(int id) {
        return roleRepository.findById(id);
    }
}
