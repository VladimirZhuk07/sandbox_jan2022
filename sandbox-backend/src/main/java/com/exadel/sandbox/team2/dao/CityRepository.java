package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.City;
import com.exadel.sandbox.team2.dto.report.ReportOnCityDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByCountryName(String countryName);

    @Modifying
    @Query(value = "select ci.name as cityName, o.name as officeName, o.address as officeAddress, COUNT(b.userId) as numberOfBooked"
            + " from City ci join Office o on ci.id = o.cityId"
            + " join Map m on o.id = m.officeId "
            + " join Workplace w on m.id = w.mapId"
            + " left join Booking b on w.id = b.workplaceId"
            + " where ci.id = :idOfCity AND IF(b.startDate IS null, 1, b.startDate between :dateFrom AND :dateTo)"
            + " GROUP BY ci.name, o.name, o.address", nativeQuery = true)
    List<ReportOnCityDto> getDataForReportOnCity(@Param("idOfCity") Long idOfCity,
                                                 @Param("dateFrom") LocalDate bookedDateFrom,
                                                 @Param("dateTo") LocalDate bookedDateTo);
}
