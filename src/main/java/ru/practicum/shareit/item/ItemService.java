package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto getItemById(Long id);
    List<ItemDto> getItemsByUserId(Long userId);
    ItemDto addItem(ItemDto item, Long userId);
    ItemDto updateItem(ItemDto item, Long userId);
    List<ItemDto> getItemsByText(String text);
    void deleteItem(Long id);

}
