package ru.practicum.shareit.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.BadArgumentException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMemoryRepository repo;
    private final ItemService itemService;

    public List<UserDto> findAll() {
        return repo.findAll().stream()
                .map(UserMapper::toUserDto)
                .toList();
    }

    public UserDto findById(@Positive Long userId) {
        return UserMapper.toUserDto(repo.findById(userId));
    }

    public UserDto create(@NotNull UserDto newUser) {
        if (newUser.getEmail().isEmpty() || newUser.getEmail().isBlank())
            throw new BadArgumentException("Email не может быть пустым");

        if (newUser.getName().isEmpty() || newUser.getName().isBlank())
            throw new BadArgumentException("Имя не может быть пустым");

        return UserMapper.toUserDto(repo.create(UserMapper.toUser(newUser)));
    }


    public UserDto update(@Positive Long userId, @NotNull UserDto newUser) {
        newUser.setId(userId);
        return UserMapper.toUserDto(repo.update(UserMapper.toUser(newUser)));
    }

    public void delete(@Positive Long userId) {
        repo.delete(userId);
        List<ItemDto> deletedUserItems = itemService.getItemsByUserId(userId);
        deletedUserItems.forEach(item -> itemService.deleteItem(item.getId()));
    }
}
