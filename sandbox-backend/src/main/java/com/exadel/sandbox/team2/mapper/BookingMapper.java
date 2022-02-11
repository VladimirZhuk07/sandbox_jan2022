package com.exadel.sandbox.team2.mapper;

import com.exadel.sandbox.team2.domain.Booking;
import com.exadel.sandbox.team2.dto.BookingDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingDto toDto(Booking booking);
    Booking toEntity(BookingDto bookingDto);
}
