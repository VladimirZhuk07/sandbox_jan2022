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
public class BookingServiceImpl extends CRUDServiceImpl<Booking, BookingDto> implements BookingService {

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final WorkplaceRepository workplaceRepository;

    private final BookingMapper mapper;

    @Override
    public Booking save(Booking booking, BookingDto bookingDto) {
        User user = userRepository.getById(bookingDto.getUserId());
        Workplace workplace = workplaceRepository.getById(bookingDto.getWorkplaceId());
        booking = mapper.toEntity(bookingDto);
        booking.setWorkplaceId(workplace);
        booking.setUserId(user);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking update(Booking booking, BookingDto bookingDto, long id) {
        booking = bookingRepository.findById(id).get();
        if(bookingDto.getStartDate() != null){
            booking.setStartDate(booking.getStartDate());
        }if(bookingDto.getEndDate() != null){
            booking.setEndDate(bookingDto.getEndDate());
        }if(bookingDto.isRecurring() != booking.isRecurring()){
            booking.setRecurring(bookingDto.isRecurring());
        }

        return bookingRepository.save(booking);
    }

    @Override
    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }
}
