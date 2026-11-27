package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.ValidationGroups;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
public class ItemDto {
    @Null(groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    private Long id;

    @NotBlank(groups = ValidationGroups.CreateGroup.class)
    private String name;

    @NotBlank(groups = ValidationGroups.CreateGroup.class)
    private String description;

    @NotNull(groups = ValidationGroups.CreateGroup.class)
    private Boolean available;

    @Positive(groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    private Long owner;

    @Positive(groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    private Long request;
}
