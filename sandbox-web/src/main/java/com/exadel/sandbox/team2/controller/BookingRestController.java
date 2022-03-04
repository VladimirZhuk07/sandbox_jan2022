package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.dto.BookingDto;
import com.exadel.sandbox.team2.mapper.BookingMapper;
import com.exadel.sandbox.team2.serivce.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingRestController {

    private final BookingService service;

    private final BookingMapper mapper;

    @GetMapping("/{id}")
    public BookingDto findById(@PathVariable Long id) {
        return mapper.toDto(service.findById(id).get());
    }

    @GetMapping
    public List<BookingDto> findAll() {
        return service.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public BookingDto add(@RequestBody BookingDto bookingDto) {
        return service.save(bookingDto);
    }

    @PutMapping("/{id}")
    public BookingDto update(@PathVariable Long id, @RequestBody BookingDto bookingDto) {
        return service.update(bookingDto,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
