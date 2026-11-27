package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.ValidationGroups;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@AllArgsConstructor
public class ItemRequestDto {
    @Null(groups = ValidationGroups.CreateGroup.class)
    @Positive(groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    private long id;

    @NotBlank(groups = ValidationGroups.CreateGroup.class)
    private String description;

    @Positive(groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    private long requestor;

    @Null(groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    private LocalDateTime created;
}
