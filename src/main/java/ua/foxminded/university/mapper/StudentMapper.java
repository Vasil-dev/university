package ua.foxminded.university.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.foxminded.university.dto.StudentDto;
import ua.foxminded.university.model.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStudentFromDto(StudentDto dto, @MappingTarget Student student);
}
