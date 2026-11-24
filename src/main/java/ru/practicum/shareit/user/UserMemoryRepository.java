package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.BadArgumentException;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.*;

@Repository
public class UserMemoryRepository {
    private final Map<Long, User> users = new HashMap<>();
    private final List<Long> deletedIds = new ArrayList<>();

    public List<User> findAll() {
        return users.values().stream().toList();
    }

    public User findById(Long userId) {
        if (users.containsKey(userId))
            return users.get(userId);
        throw new NotFoundException("Пользователя с id = " + userId + " нет");
    }

    public User create(User newUser) {
        if (emailIsUses(newUser.getEmail()))
            throw new BadArgumentException("Этот email уже используеться");
        newUser.setId(getIdForCreate());
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    private Long getIdForCreate() {
        if (deletedIds.isEmpty()) return users.size() + 1L;
        Long id = deletedIds.getFirst();
        deletedIds.remove(id);
        return id;
    }

    private boolean emailIsUses(String email) {
        Optional<User> e = users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
        return e.isPresent();
    }

    public User update(User newUser) {
        if (!users.containsKey(newUser.getId()))
            throw new NotFoundException("Пользователя с id = " + newUser.getId() + " нет");
        User user = users.get(newUser.getId());
        if (newUser.getName() != null && !newUser.getName().isBlank())
            user.setName(newUser.getName());
        if (newUser.getEmail() != null && !newUser.getEmail().isBlank()) {
            if (emailIsUses(newUser.getEmail()))
                throw new BadArgumentException("Этот email уже используеться");
            user.setEmail(newUser.getEmail());
        }
        return user;
    }

    public void delete(Long userId) {
        if (!users.containsKey(userId))
            throw new NotFoundException("Пользователя с id = " + userId + " нет");
        deletedIds.add(userId);
        users.remove(userId);
    }

}
