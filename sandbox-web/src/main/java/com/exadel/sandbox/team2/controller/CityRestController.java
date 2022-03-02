package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.domain.City;
import com.exadel.sandbox.team2.dto.CityDto;
import com.exadel.sandbox.team2.mapper.CityMapper;
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
    private final CityMapper mapper;

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
        return mapper.toDto(service.save(null, entity));
    }

    @PutMapping("/{id}")
    public City update(@PathVariable Long id, @RequestBody CityDto cityDto){
        return service.update(null, cityDto, id);
    }

}
