package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {
    List<BookingDto> findAll();

    BookingDto findById(Long bookingId);

    BookingDto create(BookingDto newBooking, Long userId);

    BookingDto update(Long bookingId, Long userId, BookingDto newBooking);

    BookingDto confirmStatus(Long bookingId, Long userId, Boolean approve, String review);

    void delete(Long bookingId);
}
