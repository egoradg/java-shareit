package ru.practicum.shareit.request;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRequestMemoryRepository {
    private final Map<Long, ItemRequest> requests = new HashMap<>();
    private final List<Long> deletedIds = new ArrayList<>();

    public List<ItemRequest> findAll() {
        return requests.values().stream().toList();
    }

    public ItemRequest findById(Long requestId) {
        if (requests.containsKey(requestId))
            return requests.get(requestId);
        throw new NotFoundException("Запроса с id = " + requestId + " нет");
    }

    public ItemRequest create(ItemRequest newRequest) {
        newRequest.setCreated(LocalDateTime.now());
        newRequest.setId(getIdForCreate());
        requests.put(newRequest.getId(), newRequest);
        return newRequest;
    }

    private Long getIdForCreate() {
        if (deletedIds.isEmpty()) return requests.size() + 1L;
        Long id = deletedIds.getFirst();
        deletedIds.remove(id);
        return id;
    }

    public ItemRequest update(ItemRequest newRequest) {
        requests.put(newRequest.getId(), newRequest);
        return newRequest;
    }

    public void delete(Long requestId) {
        if (!requests.containsKey(requestId))
            throw new NotFoundException("Запроса с id = " + requestId + " нет");
        deletedIds.add(requestId);

        requests.remove(requestId);
    }
}
