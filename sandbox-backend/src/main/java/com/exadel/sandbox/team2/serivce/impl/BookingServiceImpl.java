package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.BookingRepository;
import com.exadel.sandbox.team2.dao.UserRepository;
import com.exadel.sandbox.team2.dao.WorkplaceRepository;
import com.exadel.sandbox.team2.domain.Booking;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.Workplace;
import com.exadel.sandbox.team2.dto.BookingDto;
import com.exadel.sandbox.team2.mapper.BookingMapper;
import com.exadel.sandbox.team2.serivce.base.CRUDServiceImpl;
import com.exadel.sandbox.team2.serivce.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl extends CRUDServiceImpl<Booking> implements BookingService {

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final WorkplaceRepository workplaceRepository;

    private final BookingMapper mapper;

    @Override
    public BookingDto save(BookingDto bookingDto) {
        User user = userRepository.findById(bookingDto.getUserId()).get();
        Workplace workplace = workplaceRepository.findById(bookingDto.getWorkplaceId()).get();
        if(workplace.getBookingId() == null && user.getBookingId() == null) {
            Booking booking = mapper.toEntity(bookingDto);
            booking.setWorkplaceId(workplace);
            booking.setUserId(user);
            Booking newBooking = bookingRepository.save(booking);
            return mapper.toDto(newBooking);
        }

        return null;
    }

    @Override
    public BookingDto update(long id, BookingDto bookingDto) {
        Booking booking = bookingRepository.findById(id).get();
        if(bookingDto.getStartDate() != null){
            booking.setStartDate(booking.getStartDate());
        }if(bookingDto.getEndDate() != null){
            booking.setEndDate(bookingDto.getEndDate());
        }if(bookingDto.isRecurring() != booking.isRecurring()){
            booking.setRecurring(bookingDto.isRecurring());
        }

        return mapper.toDto(bookingRepository.save(booking));
    }

    @Override
    public void remove(long id) {
        Booking booking = bookingRepository.findById(id).get();
        User user = booking.getUserId();
        user.setBookingId(null);
        userRepository.save(user);
        Workplace workplace = booking.getWorkplaceId();
        workplace.setBookingId(null);
        workplaceRepository.save(workplace);

        bookingRepository.deleteById(id);
    }
}
