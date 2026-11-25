package ru.practicum.shareit.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.ValidationGroups;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final ItemRequestService service;

    @GetMapping
    public List<ItemRequestDto> getAllRequests() {
        return service.getAllRequests();
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestById(@PathVariable @Positive Long requestId) {
        return service.getRequestById(requestId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRequestDto createRequest(
            @RequestBody @Validated(ValidationGroups.CreateGroup.class) ItemRequestDto newRequest) {
        return service.create(newRequest);
    }

    @PatchMapping("/{requestId}")
    public ItemRequestDto updateRequest(
            @PathVariable @Positive Long requestId,
            @RequestBody @Validated(ValidationGroups.UpdateGroup.class) ItemRequestDto newRequest) {
        return service.updateRequest(requestId, newRequest);
    }

    @DeleteMapping("/{requestId}")
    public void deleteRequest(@PathVariable @Positive Long requestId) {
        service.delete(requestId);
    }
}
