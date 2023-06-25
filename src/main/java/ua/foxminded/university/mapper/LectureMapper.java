package ua.foxminded.university.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.foxminded.university.dto.LectureDto;
import ua.foxminded.university.model.Lecture;

@Mapper(componentModel = "spring")
public interface LectureMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLectureFromDto(LectureDto dto, @MappingTarget Lecture lecture);
}
