package com.exadel.sandbox.team2.mapper;

import com.exadel.sandbox.team2.domain.Booking;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.Workplace;
import com.exadel.sandbox.team2.dto.BookingDto;
import com.exadel.sandbox.team2.dto.telegram.CreateBookingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "workplace", target = "workplaceId", qualifiedByName = "getWorkplaceId")
    @Mapping(source = "user", target = "userId", qualifiedByName = "getUserId")
    BookingDto toDto(Booking booking);
    Booking toEntity(BookingDto bookingDto);

    Booking CreatedBookingDtoToEntity(CreateBookingDto createBookingDto);

    @Named("getWorkplaceId")
    static long getWorkplaceId(Workplace workplace) {
        return workplace.getId();
    }

    @Named("getUserId")
    static long getUserId(User user){
        return user.getId();
    }
}
