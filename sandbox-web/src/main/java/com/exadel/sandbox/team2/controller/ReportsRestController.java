package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.dto.report.*;
import com.exadel.sandbox.team2.serivce.service.CityService;
import com.exadel.sandbox.team2.serivce.service.MapService;
import com.exadel.sandbox.team2.serivce.service.OfficeService;
import com.exadel.sandbox.team2.serivce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportsRestController {

    private final UserService userService;
    private final OfficeService officeService;
    private final MapService mapService;
    private final CityService cityService;

    @GetMapping("/getDataForUsersReport")
    public List<ReportOnUsersDto> getDataForReportOnUsers(
            @RequestParam("userBookDateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date userBookDateFrom,
            @RequestParam("userBookDateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date userBookDateTo) {
        return userService.getDataForReportOnUsers(userBookDateFrom, userBookDateTo);
    }

    @GetMapping("/getDataForEmployeesReport")
    public List<ReportOnEmployeesDto> getDataForReportOnEmployees(
            @RequestParam("userCreateDateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date userCreateDateFrom,
            @RequestParam("userCreateDateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date userCreateDateTo) {
        return userService.getDataForEmployeesReport(userCreateDateFrom, userCreateDateTo);
    }

    @GetMapping("/getDataForReportBySingleOffice")
    public List<ReportOnSingleOfficeDto> getDataForReportOnSingleOffice(
            @RequestParam("idOfOffice") Long idOfOffice,
            @RequestParam("modifiedFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date modifiedDateFrom,
            @RequestParam("modifiedTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date modifiedDateTo) {
        return officeService.getDataForReportBySingleOffice(idOfOffice, modifiedDateFrom, modifiedDateTo);
    }

    @GetMapping("/getDataForReportByAllOffices")
    public List<ReportOnAllOfficesDto> getDataForReportOnAllOffices(
            @RequestParam("modifiedFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date modifiedDateFrom,
            @RequestParam("modifiedTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date modifiedDateTo) {
        return officeService.getDataForReportByAllOffices(modifiedDateFrom, modifiedDateTo);
    }

    @GetMapping("/getDataForReportOnFloor")
    public List<ReportOnFloorDto> getDataForReportOnFloor(@RequestParam("numberOfFloor") Long numberOfFloor,
                                                          @RequestParam("bookingDateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date bookingDateFrom,
                                                          @RequestParam("bookingDateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date bookingDateTo) {
        return mapService.getDataForReportOnFloor(numberOfFloor, bookingDateFrom, bookingDateTo);
    }

    @GetMapping("/getDataForReportOnCity")
    public List<ReportOnCityDto> getDataForReportOnCity(@RequestParam("idOfCity") Long idOfCity,
                                                        @RequestParam("DateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                                        @RequestParam("DateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo) {
        return cityService.getDataForReportOnCity(idOfCity, dateFrom, dateTo);
    }
}
