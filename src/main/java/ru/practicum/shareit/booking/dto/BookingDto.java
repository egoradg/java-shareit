package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.ValidationGroups;
import ru.practicum.shareit.booking.BookingStatus;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@AllArgsConstructor
public class BookingDto {
    private long id;

    @NotNull(groups = ValidationGroups.CreateGroup.class)
    @FutureOrPresent(groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    private LocalDateTime start;

    @NotNull(groups = ValidationGroups.CreateGroup.class)
    @FutureOrPresent(groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    private LocalDateTime end;

    @NotNull(groups = ValidationGroups.CreateGroup.class)
    @Positive(groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    private long item;

    @NotNull(groups = ValidationGroups.CreateGroup.class)
    @Positive(groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    private long booker;

    @Null
    private BookingStatus bookingStatus;

    @Null
    private String review;
}
