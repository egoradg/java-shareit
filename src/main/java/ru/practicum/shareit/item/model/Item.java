package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
public class Item {
    private final Long id;
    private String name;
    private String description;
    private boolean available;
    private final Long owner;
    private Long request;
}
