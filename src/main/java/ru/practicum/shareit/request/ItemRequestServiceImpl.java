package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestMemoryRepository repo;

    @Override
    public ItemRequestDto getRequestById(long requestId) {
        return ItemRequestMapper.toItemRequestDto(repo.findById(requestId));
    }

    @Override
    public List<ItemRequestDto> getAllRequests() {
        return repo.findAll().stream().map(ItemRequestMapper::toItemRequestDto).toList();
    }

    @Override
    public ItemRequestDto create(ItemRequestDto itemRequestDto) {
        return ItemRequestMapper.toItemRequestDto(repo.create(ItemRequestMapper.toItemRequest(itemRequestDto)));
    }

    @Override
    public ItemRequestDto updateRequest(Long requestId, ItemRequestDto newRequest) {
        ItemRequest request = repo.findById(requestId);

        if (newRequest.getDescription() != null && !newRequest.getDescription().isBlank()) {
            request.setDescription(newRequest.getDescription());
            request.setCreated(LocalDateTime.now());
        }
        return ItemRequestMapper.toItemRequestDto(repo.update(request));
    }

    @Override
    public void delete(Long requestId) {
        repo.delete(requestId);
    }
}
