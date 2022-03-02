package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.domain.Country;
import com.exadel.sandbox.team2.dto.CountryDto;
import com.exadel.sandbox.team2.mapper.CountryMapper;
import com.exadel.sandbox.team2.serivce.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryRestController {

    private final CountryService service;
    private final CountryMapper mapper;

    @GetMapping("/{id}")
    public Optional<Country> getById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<Country> getAll() {
        return service.findAll();
    }

    @PostMapping
    public CountryDto save(@RequestBody Country entity) {
        return mapper.toDto(service.save(entity,null));
    }

    @PutMapping("/{id}")
    public Country update(@PathVariable long id, @RequestBody CountryDto countryDto){
        return service.update(null, countryDto, id);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}
