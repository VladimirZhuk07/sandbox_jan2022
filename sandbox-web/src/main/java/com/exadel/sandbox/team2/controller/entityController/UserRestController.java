package com.exadel.sandbox.team2.controller.entityController;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.dto.UserDto;
import com.exadel.sandbox.team2.mapper.UserMapper;
import com.exadel.sandbox.team2.serivce.base.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final CRUDService<User> crudService;

    private final UserMapper mapper;

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        Optional<User> user = crudService.findById(id);
        return mapper.toDto(user.get());
    }

    @GetMapping
    public List<UserDto> findAll() {
        List<User> list = crudService.findAll();
        return list.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public UserDto add(@RequestBody UserDto userDto) {
        User user = mapper.toEntity(userDto);
        User newUser = crudService.save(user);
        return mapper.toDto(newUser);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserDto userDto) {
        User newUser = mapper.toEntity(userDto);
        newUser.setId(id);
        crudService.update(newUser);
        return mapper.toDto(newUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        crudService.delete(id);
    }
}
