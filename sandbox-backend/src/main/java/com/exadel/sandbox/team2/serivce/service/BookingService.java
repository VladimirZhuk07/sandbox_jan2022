package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.Booking;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.dto.BookingDto;
import com.exadel.sandbox.team2.dto.telegramDto.CreateBookingDto;
import com.exadel.sandbox.team2.serivce.base.BaseDtoService;

public interface BookingService extends BaseDtoService<Booking, BookingDto> {
    boolean save(CreateBookingDto dto, User user);
}
