package ua.foxminded.university.mapper;

import org.mapstruct.*;
import ua.foxminded.university.dto.TeacherDto;
import ua.foxminded.university.model.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTeacherFromDto(TeacherDto dto, @MappingTarget Teacher entity);
}