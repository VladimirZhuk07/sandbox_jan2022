package com.exadel.sandbox.team2.controller.entityController;

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

    private final WorkplaceService workplaceService;

    private final WorkplaceMapper mapper;

    @GetMapping("/{id}")
    public WorkplaceDto findById(@PathVariable Long id) {
        return mapper.toDto(workplaceService.findById(id).get());
    }

    @GetMapping
    public List<WorkplaceDto> findAll() {
        return workplaceService.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public WorkplaceDto add(@RequestBody WorkplaceDto workplaceDto) {
        return mapper.toDto(workplaceService.save(null, workplaceDto));
    }

    @PutMapping("/{id}")
    public WorkplaceDto update(@PathVariable Long id, @RequestBody WorkplaceDto workplaceDto) {
        return mapper.toDto(workplaceService.update(null, workplaceDto, id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        workplaceService.delete(id);
    }
}
