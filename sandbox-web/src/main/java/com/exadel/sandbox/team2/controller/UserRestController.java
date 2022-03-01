package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.dto.UserDto;
import com.exadel.sandbox.team2.mapper.UserMapper;
import com.exadel.sandbox.team2.serivce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService service;

    private final UserMapper mapper;

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return mapper.toDto(service.findById(id).get());
    }

    @GetMapping
    public List<UserDto> findAll() {
        List<User> list = service.findAll();
        return list.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public UserDto add(@RequestBody UserDto userDto) {
        return mapper.toDto(service.save(mapper.toEntity(userDto),null));
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserDto userDto) {
        User newUser = mapper.toEntity(userDto);
        newUser.setId(id);
        service.update(mapper.toEntity(userDto), null, 0);
        return mapper.toDto(newUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
