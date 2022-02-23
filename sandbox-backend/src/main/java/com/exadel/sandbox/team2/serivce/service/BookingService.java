package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.Booking;
import com.exadel.sandbox.team2.dto.BookingDto;
import com.exadel.sandbox.team2.serivce.base.CRUDService;

public interface BookingService extends CRUDService<Booking> {

    BookingDto save(BookingDto bookingDto);

    BookingDto update(long id, BookingDto bookingDto);

    void remove(long id);
}