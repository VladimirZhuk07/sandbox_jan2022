package com.exadel.sandbox.team2.controller;

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
        return service.saveDto(entity);
    }

    @PutMapping("/{id}")
    public OfficeDto update(@PathVariable Long id, @RequestBody OfficeDto entity) {
        return service.updateDto(entity,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/cities/{id}")
    public List<Office> getAllByCity(@PathVariable Long id){
        return service.findByCityId(id);
    }

    @Transactional
    @DeleteMapping("/cities/{id}")
    public void deleteByCity(@PathVariable long id){
        service.deleteByCity(id);
    }

}
