package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.domain.City;
import com.exadel.sandbox.team2.dto.CityDto;
import com.exadel.sandbox.team2.serivce.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityRestController {

    private final CityService service;

    @GetMapping("/{id}")
    public Optional<City> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<City> getAll() {
        return service.findAll();
    }

    @PostMapping
    public CityDto save(@RequestBody CityDto entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public CityDto update(@PathVariable Long id, @RequestBody CityDto cityDto) {
        return service.update(cityDto, id);
    }

}
