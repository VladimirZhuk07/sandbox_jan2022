package com.exadel.sandbox.team2.controller.entityController;

import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.dto.OfficeDto;
import com.exadel.sandbox.team2.mapper.OfficeMapper;
import com.exadel.sandbox.team2.serivce.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offices")
@RequiredArgsConstructor
public class OfficeRestController {

    private final OfficeService service;

    private final OfficeMapper mapper;

    @GetMapping("/{id}")
    public Office get(@PathVariable Long id) {
        return service.findById(id).orElse(null);
    }

    @GetMapping
    public List<Office> getAll() {
        return service.findAll();
    }

    @PostMapping
    public OfficeDto save(@RequestBody OfficeDto entity) {
        return mapper.toDto(service.save(mapper.toEntity(entity)));
    }

    @PutMapping("/{id}")
    public OfficeDto update(@PathVariable Long id, @RequestBody OfficeDto entity) {
        return service.updateOffice(entity, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @Transactional
    @DeleteMapping("/country/{countryName}")
    public void deleteByCountry(@PathVariable String countryName){
        service.deleteByCountry(countryName);
    }

}
