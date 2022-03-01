package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.domain.Country;
import com.exadel.sandbox.team2.dto.CountryDto;
import com.exadel.sandbox.team2.serivce.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryRestController {

    private final CountryService service;

    @GetMapping("/{name}")
    public Country getByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @GetMapping
    public List<Country> getAll() {
        return service.findAll();
    }

    @PostMapping
    public CountryDto save(@RequestBody CountryDto entity) {
        return service.save(entity);
    }

    @Transactional
    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name) {
        service.deleteByName(name);
    }

}
