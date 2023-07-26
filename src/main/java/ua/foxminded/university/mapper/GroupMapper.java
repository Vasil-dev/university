package ua.foxminded.university.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.foxminded.university.dto.GroupDto;
import ua.foxminded.university.model.Group;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGroupFromDto(GroupDto dto, @MappingTarget Group group);
}
