package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemMemoryRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private final List<Long> deletedIds = new ArrayList<>();

    public Item findById(Long itemId) {
        if (items.containsKey(itemId))
            return items.get(itemId);
        throw new NotFoundException("Вещи с id = " + itemId + " нет");
    }

    public List<Item> findByUserId(Long userId) {
        return items.values().stream()
                .filter(item -> item.getOwner().equals(userId))
                .toList();
    }

    public Item create(Item newItem) {
        newItem.setId(getIdForCreate());
        items.put(newItem.getId(), newItem);
        return newItem;
    }

    private Long getIdForCreate() {
        if (deletedIds.isEmpty()) return items.size() + 1L;
        Long id = deletedIds.getFirst();
        deletedIds.remove(id);
        return id;
    }

    public Item update(Item newItem) {
        Item item = items.get(newItem.getId());
        if (newItem.getName() != null && !newItem.getName().isBlank())
            item.setName(newItem.getName());

        if (newItem.getDescription() != null && !newItem.getDescription().isBlank())
            item.setDescription(newItem.getDescription());

        if (newItem.getAvailable() != null) {
            item.setAvailable(newItem.getAvailable());
        }

        return item;
    }

    public void delete(Long itemId) {
        if (!items.containsKey(itemId))
            throw new NotFoundException("Вещи с id = " + itemId + " нет");
        deletedIds.add(itemId);

        items.remove(itemId);
    }

    public List<Item> findByText(String text) {
        return items.values().stream()
                .filter(item -> (item.getName().toUpperCase().contains(text)
                        || item.getDescription().toUpperCase().contains(text))
                        && item.getAvailable() == true)
                .toList();
    }
}
