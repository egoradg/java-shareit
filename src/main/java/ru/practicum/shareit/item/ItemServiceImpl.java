package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.BadArgumentException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserMemoryRepository;
import ru.practicum.shareit.user.UserService;

import java.util.List;

@Service
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

        return ItemMapper.toItemDto(repo.create(ItemMapper.toItem(item)));
    }

    @Override
    public ItemDto updateItem(ItemDto newItem, Long userId) {
        userRepo.findById(userId);
        Item item = repo.findById(newItem.getId());
        if(item.getOwner() != newItem.getOwner())
            throw new BadArgumentException("Только владелец может изменять свою вещь");

        return ItemMapper.toItemDto(repo.update(ItemMapper.toItem(newItem)));
    }

    @Override
    public List<ItemDto> getItemsByText(String text) {
        return List.of();
    }

    @Override
    public void deleteItem(Long id) {
        repo.delete(id);
    }
}
