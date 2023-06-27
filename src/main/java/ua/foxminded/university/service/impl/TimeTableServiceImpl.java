package ua.foxminded.university.service.impl;

import org.springframework.stereotype.Service;
import ua.foxminded.university.model.TimeTable;
import ua.foxminded.university.repository.TimeTableRepository;
import ua.foxminded.university.service.TimeTableService;

import java.util.List;

@Service
public class TimeTableServiceImpl implements TimeTableService {

    private final TimeTableRepository timeTableRepository;

    public TimeTableServiceImpl(TimeTableRepository timeTableRepository) {
        this.timeTableRepository = timeTableRepository;
    }

    @Override
    public TimeTable getById(long id) {
        return timeTableRepository.findById(id).orElse(null);
    }

    @Override
    public List<TimeTable> getAll() {
        return timeTableRepository.findAll();
    }

    @Override
    public TimeTable create(TimeTable timeTable) {
        return timeTableRepository.save(timeTable);
    }

    @Override
    public TimeTable update(TimeTable timeTable) {
        return timeTableRepository.save(timeTable);
    }

    @Override
    public void delete(long id) {
        timeTableRepository.deleteById(id);
    }
}
