package ru.practicum.shareit.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.ValidationGroups;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return service.findAll();
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable @Positive Long userId) {
        return service.findById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addNewUser(@RequestBody @NotNull @Validated(ValidationGroups.CreateGroup.class) UserDto newUser) {
        return service.create(newUser);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable @NotNull @Positive Long userId,
                              @RequestBody @NotNull @Validated(ValidationGroups.UpdateGroup.class) UserDto newUser) {
        System.out.println(newUser);
        return service.update(userId, newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable @Positive Long userId) {
        service.delete(userId);
    }
}
