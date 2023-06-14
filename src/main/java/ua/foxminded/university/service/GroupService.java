package ua.foxminded.university.service;

import ua.foxminded.university.model.Group;

import java.util.List;

public interface GroupService {

    Group getById(long id);

    List<Group> getAll();

    Group create(Group group);

    Group update(Group group);

    void delete(long id);

}
