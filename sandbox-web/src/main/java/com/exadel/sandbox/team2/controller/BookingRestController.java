package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.dto.BookingDto;
import com.exadel.sandbox.team2.mapper.BookingMapper;
import com.exadel.sandbox.team2.serivce.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingRestController {

    private final BookingService bookingService;

    private final BookingMapper mapper;

    @GetMapping("/{id}")
    public BookingDto findById(@PathVariable Long id) {
        return mapper.toDto(bookingService.findById(id).get());
    }

    @GetMapping
    public List<BookingDto> findAll() {
        return bookingService.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @PostMapping("/{userId}")
    public BookingDto add(@RequestBody BookingDto bookingDto, @PathVariable Long userId) {
        return bookingService.save(bookingDto, userId);
    }

    @PutMapping("/{id}/{userId}")
    public BookingDto update(@PathVariable Long id, @PathVariable Long userId, @RequestBody BookingDto bookingDto) {
        return bookingService.update(id, userId, bookingDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookingService.delete(id);
    }
}
