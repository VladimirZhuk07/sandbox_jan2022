package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.dto.WorkplaceDto;
import com.exadel.sandbox.team2.mapper.WorkplaceMapper;
import com.exadel.sandbox.team2.serivce.service.WorkplaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/workplaces")
@RequiredArgsConstructor
public class WorkplaceRestController {

    private final WorkplaceService service;
    private final WorkplaceMapper mapper;

    @GetMapping("/{id}")
    public WorkplaceDto findById(@PathVariable Long id) {
        return mapper.toDto(service.findById(id).get());
    }

    @GetMapping
    public List<WorkplaceDto> findAll() {
        return service.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public WorkplaceDto add(@RequestBody WorkplaceDto workplaceDto) {
        return service.save(workplaceDto);
    }

    @PutMapping("/{id}")
    public WorkplaceDto update(@PathVariable Long id, @RequestBody WorkplaceDto workplaceDto) {
        return service.update(workplaceDto,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
