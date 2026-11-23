package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.BadArgumentException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserMemoryRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemMemoryRepository repo;
    private final UserMemoryRepository userRepo;

    @Override
    public ItemDto getItemById(Long id) {
        return ItemMapper.toItemDto(repo.findById(id));
    }

    @Override
    public List<ItemDto> getItemsByUserId(Long userId) {
        userRepo.findById(userId);

        return repo.findByUserId(userId).stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    @Override
    public ItemDto addItem(ItemDto item, Long userId) {
        userRepo.findById(userId);
        item.setOwner(userId);
        Item createdItem = repo.create(ItemMapper.toItem(item));
        log.info("Вещь добавлена:\n" + createdItem);
        return ItemMapper.toItemDto(createdItem);
    }

    @Override
    public ItemDto updateItem(ItemDto newItem, Long itemId, Long userId) {
        userRepo.findById(userId);
        Item item = repo.findById(itemId);
        newItem.setId(itemId);
        if (item.getOwner() != userId)
            throw new BadArgumentException("Только владелец может изменять свою вещь");
        Item updatedItem = repo.update(ItemMapper.toItem(newItem));
        log.info("Изменённая вещь:\n" + updatedItem);
        return ItemMapper.toItemDto(updatedItem);
    }

    @Override
    public List<ItemDto> getItemsByText(String text) {
        log.info("Поиск по тексту: " + text);
        return repo.findByText(text).stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    @Override
    public void deleteItem(Long id) {
        repo.delete(id);
        log.info("Вещь с id = " + id + " удалена");
    }
}
