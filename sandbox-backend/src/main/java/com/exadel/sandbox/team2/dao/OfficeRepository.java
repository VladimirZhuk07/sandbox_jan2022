package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.dto.report.ReportOnAllOfficesDto;
import com.exadel.sandbox.team2.dto.report.ReportOnSingleOfficeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
    void deleteByCityId(Long city_id);

    List<Office> findByCityId(Long city_id);

    List<Office> findByCityName(String cityName);

    @Modifying
    @Query("select o.name as officeName, co.name as countryName, ci.name as cityName,"
            + " o.address as officeAddress, o.parking as isParking, o.createdDate as createdDate, COUNT(b.user.id) as numberOfBooked"
            + " from Office as o"
            + " join Map m on o.map.id = m.office.id"
            + " join Workplace w on m.id = w.map.id"
            + " left join Booking b on w.id = b.workplace.id"
            + " join City ci on o.city.id = ci.id"
            + " join Country co on co.id = ci.country.id"
            + " WHERE o.id = :idOfOffice AND o.modifiedDate BETWEEN :modifiedDateFrom AND :modifiedDateTo"
            + " GROUP BY o.address")
    List<ReportOnSingleOfficeDto> getDataForReportOnSingleOffice(@Param("idOfOffice") Long idOfOffice,
                                                                 @Param("modifiedDateFrom") Date modifiedDateFrom,
                                                                 @Param("modifiedDateTo") Date modifiedDateTo);

    @Modifying
    @Query("select o.id as id, o.name as officeName, co.name as countryName, ci.name as cityName,"
            + " o.address as officeAddress, o.parking as isParking, o.createdDate as createdDate, COUNT(b.user.id) as numberOfBooked"
            + " from Office o "
            + " left join Map m on o.id = m.office.id"
            + " left join Workplace w on m.id = w.map.id"
            + " left join Booking b on w.id = b.workplace.id"
            + " left join City ci on o.city.id = ci.id"
            + " left join Country co on co.id = ci.country.id"
            + " WHERE o.modifiedDate BETWEEN :modifiedDateFrom AND :modifiedDateTo"
            + " GROUP BY o.address")
    List<ReportOnAllOfficesDto> getDataForReportOnAllOffices(@Param("modifiedDateFrom") Date modifiedDateFrom,
                                                             @Param("modifiedDateTo") Date modifiedDateTo);
}
