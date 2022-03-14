package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.dao.UserRepository;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.dto.report.ReportOnEmployeesDto;
import com.exadel.sandbox.team2.dto.UserDto;
import com.exadel.sandbox.team2.mapper.UserMapper;
import com.exadel.sandbox.team2.report.ReportService;
import com.exadel.sandbox.team2.serivce.impl.ReportServiceImpl;
import com.exadel.sandbox.team2.serivce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.NotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService service;
    private final ReportService reportService;
    private final ReportServiceImpl rp;
    private final UserRepository userRepository;

    private final UserMapper mapper;

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        Optional<User> user = service.findById(id);
        return user.map(mapper::toDto)
                .orElseThrow(NotFoundException::new);
    }

    @SneakyThrows
    @GetMapping
    public List<UserDto> findAll() {
        List<User> list = service.findAll();
        //System.out.println(reportService.getReport(list,"users.jrxml","123456789"));
        return list.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public UserDto add(@RequestBody UserDto userDto) {
        return service.save(userDto);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserDto userDto) {
        return service.update(userDto,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/getDataForEmployeesReport")
    public List<ReportOnEmployeesDto> getDataForEmployeesReport(
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date userCreationDateFrom,
            @RequestParam("To") @DateTimeFormat(pattern = "yyyy-MM-dd") Date userCreationDateTo) {
        return userRepository.getDataForEmployeesReport(userCreationDateFrom, userCreationDateTo);
    }
}
