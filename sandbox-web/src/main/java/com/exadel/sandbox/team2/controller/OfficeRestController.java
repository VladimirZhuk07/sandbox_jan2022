package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.dao.OfficeRepository;
import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.dto.OfficeDto;
import com.exadel.sandbox.team2.dto.report.ReportOnAllOfficesDto;
import com.exadel.sandbox.team2.dto.report.ReportOnSingleOfficeDto;
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
        return service.update(entity, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/cities/{id}")
    public List<Office> getAllByCity(@PathVariable Long id) {
        return service.findByCityId(id);
    }

    @Transactional
    @DeleteMapping("/cities/{id}")
    public void deleteByCity(@PathVariable long id) {
        service.deleteByCity(id);
    }

    @GetMapping("/getDataForReportBySingleOffice")
    public List<ReportOnSingleOfficeDto> getDataForReportOnSingleOffice(
            @RequestParam("idOfOffice") Long idOfOffice,
            @RequestParam("modifiedFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date modifiedDateFrom,
            @RequestParam("modifiedTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date modifiedDateTo) {
        return officeRepository.getDataForReportOnSingleOffice(idOfOffice, modifiedDateFrom, modifiedDateTo);
    }

    @GetMapping("/getDataForReportByAllOffices")
    public List<ReportOnAllOfficesDto> getDataForReportOnAllOffices(
            @RequestParam("modifiedFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date modifiedDateFrom,
            @RequestParam("modifiedTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date modifiedDateTo) {
        return officeRepository.getDataForReportOnAllOffices(modifiedDateFrom, modifiedDateTo);
    }

}
