package ru.practicum.shareit.item;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

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

    @PostMapping
    public ItemDto getItemById(@PathVariable Long itemId) {
        return service.getItemById(itemId);
    }

    @PostMapping
    public List<ItemDto> getItemByText(@RequestParam String text) {
        return service.getItemsByText(text);
    }

    @PostMapping
    public ItemDto addItem(@RequestBody ItemDto item, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return service.addItem(item, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestBody ItemDto item, @PathVariable Long itemId) {
        return service.addItem(item, itemId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        service.deleteItem(itemId);
    }
}
