package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.domain.Workplace;
import com.exadel.sandbox.team2.dto.WorkplaceDto;
import com.exadel.sandbox.team2.mapper.WorkplaceMapper;
import com.exadel.sandbox.team2.serivce.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/workplaces")
@RequiredArgsConstructor
public class WorkplaceRestController {

    private final CRUDService<Workplace> crudService;

    private final WorkplaceMapper mapper;

    @GetMapping("/{id}")
    public WorkplaceDto findById(@PathVariable Long id) {
        Optional<Workplace> workplace = crudService.findById(id);
        return mapper.toDto(workplace.get());
    }

    @GetMapping
    public List<WorkplaceDto> findAll() {
        List<Workplace> list = crudService.findAll();
        return list.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public WorkplaceDto add(@RequestBody WorkplaceDto workplaceDto) {
        Workplace workplace = mapper.toEntity(workplaceDto);
        Workplace newWorkplace = crudService.save(workplace);
        return mapper.toDto(newWorkplace);
    }

    @PutMapping("/{id}")
    public WorkplaceDto update(@PathVariable Long id, @RequestBody WorkplaceDto workplaceDto) {
        Workplace newWorkplace = mapper.toEntity(workplaceDto);
        newWorkplace.setId(id);
        crudService.update(newWorkplace);
        return mapper.toDto(newWorkplace);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        crudService.delete(id);
    }
}
