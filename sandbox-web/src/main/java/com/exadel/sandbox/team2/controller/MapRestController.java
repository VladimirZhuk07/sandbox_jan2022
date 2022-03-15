package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.dao.MapRepository;
import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.dto.MapDto;
import com.exadel.sandbox.team2.dto.report.ReportOnFloorDto;
import com.exadel.sandbox.team2.serivce.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/maps")
@RequiredArgsConstructor
public class MapRestController {

    private final MapService service;
    private final MapRepository mapRepository;

    @GetMapping("/{id}")
    public Map get(@PathVariable Long id) {
        return service.findById(id).orElse(null);
    }

    @GetMapping
    public List<Map> getAll() {
        return service.findAll();
    }

    @PutMapping("/{id}")
    public MapDto update(@PathVariable Long id, @RequestBody MapDto mapDto) {
        return service.update(mapDto, id);
    }

    @PostMapping
    public MapDto save(@RequestBody MapDto entity) {
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @DeleteMapping("/office/{officeId}")
    public void deleteByOfficeId(@PathVariable long officeId) {
        service.deleteByOfficeId(officeId);
    }

    @GetMapping("/getDataForReportOnFloor")
    public List<ReportOnFloorDto> getDataForReportOnFloor(@RequestParam("numberOfFloor") Long numberOfFloor,
                                                          @RequestParam("bookingDateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date bookingDateFrom,
                                                          @RequestParam("bookingDateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date bookingDateTo) {
        return mapRepository.getDataForReportOnFloor(numberOfFloor, bookingDateFrom, bookingDateTo);
    }

}
