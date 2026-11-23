package ru.practicum.shareit.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.ValidationGroups;

@Data
@AllArgsConstructor
public class UserDto {
    @Positive(groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    private Long id;

    @NotBlank(groups = ValidationGroups.CreateGroup.class)
    private String name;

    @Email(groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    @NotNull(groups = ValidationGroups.CreateGroup.class)
    private String email;
}
