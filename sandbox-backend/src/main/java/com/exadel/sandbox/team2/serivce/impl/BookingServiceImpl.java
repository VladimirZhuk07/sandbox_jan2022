package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.BookingRepository;
import com.exadel.sandbox.team2.dao.UserRepository;
import com.exadel.sandbox.team2.dao.WorkplaceRepository;
import com.exadel.sandbox.team2.domain.Booking;
import com.exadel.sandbox.team2.dto.BookingDto;
import com.exadel.sandbox.team2.mapper.BookingMapper;
import com.exadel.sandbox.team2.serivce.BookingService;
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
    public BookingDto save(BookingDto bookingDto, Long userId) {
        Booking booking = mapper.toEntity(bookingDto);
        booking.setWorkplace(workplaceRepository.findById(bookingDto.getWorkplaceId()).get());
        booking.setUser(userRepository.findById(userId).get());
        Booking newBooking = bookingRepository.save(booking);
        return mapper.toDto(newBooking);
    }

    @Override
    public BookingDto update(Long id, Long userId, BookingDto bookingDto) {
        Booking newBooking = mapper.toEntity(bookingDto);
        newBooking.setId(id);
        newBooking.setWorkplace(workplaceRepository.findById(bookingDto.getWorkplaceId()).get());
        newBooking.setUser(userRepository.findById(userId).get());
        bookingRepository.save(newBooking);
        return mapper.toDto(newBooking);
    }

    @Override
    public void delete(Long id) {
        Booking booking = bookingRepository.findById(id).get();
        booking.setUser(null);
        booking.setWorkplace(null);
        bookingRepository.deleteById(id);
    }
}
