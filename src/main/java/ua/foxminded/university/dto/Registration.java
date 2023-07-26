package ua.foxminded.university.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import ua.foxminded.university.model.Role;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class Registration {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;

    private List<Role> roles;
}
