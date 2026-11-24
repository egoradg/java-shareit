package ru.practicum.shareit.item;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.ValidationGroups;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService service;

    @GetMapping
    public List<ItemDto> getUsersItems(@RequestHeader("X-Sharer-User-Id") @Positive Long userId) {
        return service.getItemsByUserId(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable Long itemId) {
        return service.getItemById(itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemByText(@RequestParam String text) {
        if (text.isBlank()) return new ArrayList<>();
        return service.getItemsByText(text);
    }

    @PostMapping
    public ItemDto addItem(
            @RequestBody @Validated(ValidationGroups.CreateGroup.class) ItemDto item,
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return service.addItem(item, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(
            @RequestBody @Validated(ValidationGroups.UpdateGroup.class) ItemDto item,
            @PathVariable Long itemId, @RequestHeader("X-Sharer-User-Id") @Positive Long userId) {
        return service.updateItem(item, itemId, userId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        service.deleteItem(itemId);
    }
}
