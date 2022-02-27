package com.exadel.sandbox.team2.controller.entityController;

import com.exadel.sandbox.team2.domain.City;
import com.exadel.sandbox.team2.dto.CityDto;
import com.exadel.sandbox.team2.dto.OfficeDto;
import com.exadel.sandbox.team2.serivce.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService service;

    @GetMapping("/{name}")
    public City getByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @GetMapping
    public List<City> getAll() {
        return service.findAll();
    }

    @PostMapping
    public CityDto save(@RequestBody CityDto entity) {
        return service.save(entity);
    }

    @Transactional
    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name) {
        service.deleteByName(name);
    }


}
