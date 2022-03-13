package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.dao.OfficeRepository;
import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.dto.OfficeDto;
import com.exadel.sandbox.team2.dto.report.ReportByEmployeesDto;
import com.exadel.sandbox.team2.dto.report.ReportBySingleOfficeDto;
import com.exadel.sandbox.team2.mapper.OfficeMapper;
import com.exadel.sandbox.team2.serivce.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/offices")
@RequiredArgsConstructor
public class OfficeRestController {

    private final OfficeService service;
    private final OfficeRepository officeRepository;

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
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public OfficeDto update(@PathVariable Long id, @RequestBody OfficeDto entity) {
        return service.update(entity,id);
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

    @GetMapping("/getDataForEmployeesReport")
    public List<ReportBySingleOfficeDto> getDataForReportBySingleOffice(
            @RequestParam("idOfOffice") Long idOfOffice,
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date userCreationDateFrom,
            @RequestParam("To") @DateTimeFormat(pattern = "yyyy-MM-dd") Date userCreationDateTo) {
        return officeRepository.getDataForReportBySingleOffice(idOfOffice, userCreationDateFrom, userCreationDateTo);
    }

}
