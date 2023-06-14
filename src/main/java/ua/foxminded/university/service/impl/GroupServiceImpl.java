package ua.foxminded.university.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.foxminded.university.model.Group;
import ua.foxminded.university.repository.GroupRepository;
import ua.foxminded.university.service.GroupService;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }


    @Override
    public Group getById(long id) {
        return groupRepository.findById(id).orElse(null);
    }

    @Override
    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group create(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group update(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void delete(long id) {
        groupRepository.deleteById(id);
    }
}
