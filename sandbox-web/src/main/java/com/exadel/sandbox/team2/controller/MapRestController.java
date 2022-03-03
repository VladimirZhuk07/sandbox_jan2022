package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.dto.MapDto;
import com.exadel.sandbox.team2.serivce.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maps")
@RequiredArgsConstructor
public class MapRestController {

    private final MapService service;

    @GetMapping("/{id}")
    public Map get(@PathVariable Long id) {
        return service.findById(id).orElse(null);
    }

    @GetMapping
    public List<Map> getAll() {
        return service.findAll();
    }

    @PutMapping("/{id}")
    public MapDto update(@PathVariable Long id, @RequestBody MapDto mapDto){
        return service.updateDto(mapDto, id);
    }

    @PostMapping
    public MapDto save(@RequestBody MapDto entity) {
        return service.saveDto(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @DeleteMapping("/office/{officeId}")
    public void deleteByOfficeId(@PathVariable long officeId){
        service.deleteByOfficeId(officeId);
    }

}
