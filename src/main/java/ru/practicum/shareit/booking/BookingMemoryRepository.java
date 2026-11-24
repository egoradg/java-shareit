package ru.practicum.shareit.booking;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookingMemoryRepository {
    private final Map<Long, Booking> bookings = new HashMap<>();
    private final List<Long> deletedIds = new ArrayList<>();

    public List<Booking> findAll() {
        return bookings.values().stream().toList();
    }

    public Booking findById(Long bookingId) {
        if (bookings.containsKey(bookingId))
            return bookings.get(bookingId);
        throw new NotFoundException("Бронирования с id = " + bookingId + " нет");
    }


    public Booking findByItemId(long id) {
        return bookings.values().stream()
                .filter(booking -> booking.getBookingStatus() == BookingStatus.APPROVED
                        && booking.getItem() == id)
                .findFirst().orElse(null);
    }

    public Booking create(Booking newBooking) {
        newBooking.setBookingStatus(BookingStatus.WAITING);
        newBooking.setId(getIdForCreate());
        bookings.put(newBooking.getId(), newBooking);
        return newBooking;
    }

    private Long getIdForCreate() {
        if (deletedIds.isEmpty()) return bookings.size() + 1L;
        Long id = deletedIds.getFirst();
        deletedIds.remove(id);
        return id;
    }

    public Booking update(Booking newBooking) {
        bookings.put(newBooking.getId(), newBooking);
        return newBooking;
    }

    public Booking setBookingStatus(Long bookingId, BookingStatus status) {
        Booking booking = bookings.get(bookingId);
        booking.setBookingStatus(status);
        return booking;
    }

    public void delete(Long bookingId) {
        if (!bookings.containsKey(bookingId))
            throw new NotFoundException("Вещи с id = " + bookingId + " нет");
        deletedIds.add(bookingId);

        bookings.remove(bookingId);
    }

    public void saveReview(Long bookingId, String review) {
        Booking booking = bookings.get(bookingId);
        booking.setReview(review);
    }
}
