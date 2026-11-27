package ru.practicum.shareit.booking;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.ValidationGroups;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService service;

    @GetMapping
    public List<BookingDto> getAllBookings() {
        return service.findAll();
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@PathVariable @Positive Long bookingId) {
        return service.findById(bookingId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto addNewBooking(
            @RequestHeader("X-Sharer-User-Id") @Positive Long userId,
            @RequestBody @NotNull @Validated(ValidationGroups.CreateGroup.class) BookingDto newBooking) {
        return service.create(newBooking, userId);
    }

    @PatchMapping("/{BookingId}")
    public BookingDto updateBooking(
            @PathVariable @NotNull @Positive Long bookingId,
            @RequestHeader("X-Sharer-User-Id") @Positive Long userId,
            @RequestBody @NotNull @Validated(ValidationGroups.UpdateGroup.class) BookingDto newBooking) {
        return service.update(bookingId, userId, newBooking);
    }

    @PatchMapping("/{BookingId}/status")
    public BookingDto updateBooking(
            @PathVariable @NotNull @Positive Long bookingId,
            @RequestHeader("X-Sharer-User-Id") @Positive Long userId,
            @RequestParam Boolean approve,
            @RequestParam String review) {
        return service.confirmStatus(bookingId, userId, approve, review);
    }

    @DeleteMapping("/{bookingId}")
    public void deleteBooking(@PathVariable @Positive Long bookingId) {
        service.delete(bookingId);
    }
}
