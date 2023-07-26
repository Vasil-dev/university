package ua.foxminded.university.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupDto {
    private int id;
    private String groupName;
}
