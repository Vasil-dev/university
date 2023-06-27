package ua.foxminded.university.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Registration {
    private Long id;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
