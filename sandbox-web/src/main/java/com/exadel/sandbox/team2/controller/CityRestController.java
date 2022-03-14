package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.dao.CityRepository;
import com.exadel.sandbox.team2.domain.City;
import com.exadel.sandbox.team2.dto.CityDto;
import com.exadel.sandbox.team2.dto.report.ReportOnCityDto;
import com.exadel.sandbox.team2.serivce.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityRestController {

    private final CityService service;
    private final CityRepository cityRepository;

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

    @GetMapping("/getDataForReportOnCity")
    public List<ReportOnCityDto> getDataForReportOnCity(@RequestParam("idOfCity") Long idOfCity,
                                                        @RequestParam("DateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                                        @RequestParam("DateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo) {
        return cityRepository.getDataForReportOnCity(idOfCity, dateFrom.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate(), dateTo.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
    }

}
