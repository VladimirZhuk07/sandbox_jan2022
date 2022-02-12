package com.exadel.sandbox.team2.controller.entityController;

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

}
