package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;

public class ItemRequestMapper {
    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        return new ItemRequestDto(
                itemRequest.getId(),
                itemRequest.getDescription(),
                itemRequest.getRequestor(),
                itemRequest.getCreated()
        );
    }

    public static ItemRequest toItemRequest(ItemRequestDto dto) {
        return new ItemRequest(
                dto.getId(),
                dto.getDescription(),
                dto.getRequestor(),
                dto.getCreated()
        );
    }
}