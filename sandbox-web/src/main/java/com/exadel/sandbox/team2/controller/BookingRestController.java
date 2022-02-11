package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.domain.Booking;
import com.exadel.sandbox.team2.dto.BookingDto;
import com.exadel.sandbox.team2.mapper.BookingMapper;
import com.exadel.sandbox.team2.serivce.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingRestController {

    private final CRUDService<Booking> crudService;

    private final BookingMapper mapper;

    @GetMapping("/{id}")
    public BookingDto findById(@PathVariable Long id) {
        Optional<Booking> booking = crudService.findById(id);
        return mapper.toDto(booking.get());
    }

    @GetMapping
    public List<BookingDto> findAll() {
        List<Booking> list = crudService.findAll();
        return list.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public BookingDto add(@RequestBody BookingDto bookingDto) {
        Booking booking = mapper.toEntity(bookingDto);
        Booking newBooking = crudService.save(booking);
        return mapper.toDto(newBooking);
    }

    @PutMapping("/{id}")
    public BookingDto update(@PathVariable Long id, @RequestBody BookingDto bookingDto) {
        Booking newBooking = mapper.toEntity(bookingDto);
        newBooking.setId(id);
        crudService.update(newBooking);
        return mapper.toDto(newBooking);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        crudService.delete(id);
    }
}
