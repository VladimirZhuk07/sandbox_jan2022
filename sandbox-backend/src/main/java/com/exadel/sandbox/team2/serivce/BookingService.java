package com.exadel.sandbox.team2.serivce;

import com.exadel.sandbox.team2.domain.Booking;
import com.exadel.sandbox.team2.dto.BookingDto;

public interface BookingService extends CRUDService<Booking> {

    BookingDto save(BookingDto bookingDto, Long userId);

    BookingDto update(Long id, Long userId, BookingDto bookingDto);

    void delete(Long id);
}
