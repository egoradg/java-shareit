package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.BadArgumentException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingMemoryRepository repo;
    private final UserService userService;
    private final ItemService itemService;


    @Override
    public List<BookingDto> findAll() {
        return repo.findAll().stream().map(BookingMapper::toBookingDto).toList();
    }

    @Override
    public BookingDto findById(Long bookingId) {
        return BookingMapper.toBookingDto(repo.findById(bookingId));
    }

    @Override
    public BookingDto create(BookingDto newBooking, Long userId) {
        userService.findById(userId);
        Booking currentBooking = repo.findByItemId(newBooking.getId());
        if (currentBooking != null && currentBooking.getEnd().isAfter(newBooking.getStart())) {
            throw new BadArgumentException("Вы не можете забронировать эту вещь так, как она уже забронирована до "
                    + currentBooking.getEnd());
        }
        Booking createdBooking = repo.create(BookingMapper.toBooking(newBooking));
        log.info("Добавлено новое бронирование:\n" + createdBooking);
        return BookingMapper.toBookingDto(createdBooking);
    }

    @Override
    public BookingDto update(Long bookingId, Long userId, BookingDto newBooking) {
        userService.findById(userId);
        Booking oldBooking = repo.findById(bookingId);
        if (oldBooking.getBooker() != userId)
            throw new BadArgumentException("Только владелец может изменять своё бронирование");

        if (newBooking.getStart() != null)
            oldBooking.setStart(newBooking.getStart());

        if (newBooking.getEnd() != null)
            oldBooking.setEnd(newBooking.getEnd());

        Booking updatedBooking = repo.update(oldBooking);
        log.info("Бронирование изменено:\n" + updatedBooking);
        return BookingMapper.toBookingDto(updatedBooking);
    }

    @Override
    public BookingDto confirmStatus(Long bookingId, Long userId, Boolean approve, String review) {
        userService.findById(userId);
        Booking oldBooking = repo.findById(bookingId);
        ItemDto item = itemService.getItemById(oldBooking.getItem());
        BookingStatus status = oldBooking.getBookingStatus();
        if (userId == oldBooking.getBooker() && oldBooking.getBookingStatus() == BookingStatus.WAITING) {

            status = BookingStatus.CANCELED;
        } else if (item.getOwner() == userId) {
            if (oldBooking.getBookingStatus() == BookingStatus.WAITING)
                status = approve ? BookingStatus.APPROVED : BookingStatus.REJECTED;
            if (oldBooking.getBookingStatus() == BookingStatus.APPROVED) {
                status = BookingStatus.CANCELED;
                repo.saveReview(bookingId, review);
            }
        } else {
            throw new BadArgumentException("Только владелец может изменять статус бронирования");
        }

        log.info("Бронирование с id: " + bookingId + " переведено в статус: " + status);
        return BookingMapper.toBookingDto(repo.setBookingStatus(bookingId, status));
    }

    @Override
    public void delete(Long bookingId) {
        repo.delete(bookingId);
        log.info("Бронирование удалено");
    }
}
