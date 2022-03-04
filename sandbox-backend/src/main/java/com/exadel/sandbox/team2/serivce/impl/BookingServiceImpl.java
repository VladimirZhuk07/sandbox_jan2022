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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl extends CRUDServiceImpl<Booking, BookingDto> implements BookingService {

    private final BookingRepository repository;

    private final UserRepository userRepository;

    private final WorkplaceRepository workplaceRepository;

    private final BookingMapper mapper;

    @Override
    public BookingDto save(BookingDto dto) {
        User user = userRepository.getById(dto.getUserId());
        Workplace workplace = workplaceRepository.getById(dto.getWorkplaceId());
        Booking booking = mapper.toEntity(dto);
        booking.setWorkplace(workplace);
        booking.setUser(user);
        return mapper.toDto(repository.save(booking));
    }

    @Override
    public BookingDto update(BookingDto dto, long id) {
        Booking booking = repository.findById(id).orElse(null);
        if(booking == null)
            return null;
        checkAndSet(booking, dto);
        return mapper.toDto(repository.save(booking));
    }

    @Override
    public void checkAndSet(Booking booking, BookingDto bookingDto) {
        if(bookingDto.getStartDate() != null && booking.getStartDate() != bookingDto.getStartDate()){
            booking.setStartDate(bookingDto.getStartDate());
        }
        if(bookingDto.getEndDate() != null && booking.getEndDate() != bookingDto.getEndDate()){
            booking.setEndDate(bookingDto.getEndDate());
        }
        if(bookingDto.getMonday() != null && booking.getMonday() != bookingDto.getMonday()){
            booking.setMonday(bookingDto.getMonday());
        }
        if(bookingDto.getTuesday() != null && booking.getTuesday() != bookingDto.getTuesday()){
            booking.setTuesday(bookingDto.getTuesday());
        }
        if(bookingDto.getWednesday() != null && booking.getWednesday() != bookingDto.getWednesday()){
            booking.setWednesday(bookingDto.getWednesday());
        }
        if(bookingDto.getThursday() != null && booking.getThursday() != bookingDto.getThursday()){
            booking.setThursday(bookingDto.getThursday());
        }
        if(bookingDto.getFriday() != null && booking.getFriday() != bookingDto.getFriday()){
            booking.setFriday(bookingDto.getFriday());
        }
        if(bookingDto.getSaturday() != null && booking.getSaturday() != bookingDto.getSaturday()){
            booking.setSaturday(bookingDto.getSaturday());
        }
        if(bookingDto.getSunday() != null && booking.getSunday() != bookingDto.getSunday()){
            booking.setSunday(bookingDto.getSunday());
        }
        if(bookingDto.getRecurring() != null && booking.getRecurring() != bookingDto.getRecurring()){
            booking.setRecurring(bookingDto.getRecurring());
        }
        if(bookingDto.getWorkplaceId() != 0) {
            Optional<Workplace> workplace = workplaceRepository.findById(bookingDto.getWorkplaceId());
            workplace.ifPresent(booking::setWorkplace);
        }
        if(bookingDto.getUserId() != 0){
            Optional<User> user = userRepository.findById(bookingDto.getUserId());
            user.ifPresent(booking::setUser);
        }
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
