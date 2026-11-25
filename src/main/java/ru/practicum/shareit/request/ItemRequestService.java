package ru.practicum.shareit.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {

    ItemRequestDto getRequestById(long requestId);

    List<ItemRequestDto> getAllRequests();

    ItemRequestDto create(ItemRequestDto itemRequestDto);

    ItemRequestDto updateRequest( Long requestId, ItemRequestDto newRequest);

    void delete(@Positive Long requestId);
}