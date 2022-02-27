package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.dto.UserDto;
import com.exadel.sandbox.team2.mapper.UserMapper;
import com.exadel.sandbox.team2.serivce.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Exadel Api Project")
public class UserRestController {

    private final UserMapper mapper;

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(mapper::toDto)
                   .orElseThrow(NotFoundException::new);
    }

    @GetMapping
    public List<UserDto> findAll() {
        List<User> list = userService.findAll();
        return list.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public UserDto add(@RequestBody UserDto userDto) {
        User user = mapper.toEntity(userDto);
        user = userService.save(user);
        return mapper.toDto(user);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserDto userDto) {
        User newUser = mapper.toEntity(userDto);
        newUser.setId(id);
        userService.update(newUser);
        return mapper.toDto(newUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
