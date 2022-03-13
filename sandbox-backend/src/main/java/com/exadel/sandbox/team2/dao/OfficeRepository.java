package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.dto.report.ReportByAllOfficesDto;
import com.exadel.sandbox.team2.dto.report.ReportBySingleOfficeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
    void deleteByCityId(Long city_id);

    List<Office> findByCityId(Long city_id);

    List<Office> findByCityName(String cityName);

    @Modifying
    @Query("select o.name as officeName, co.name as countryName, ci.name as cityName, o.address as officeAddress, o.parking as isParking, o.createdDate as createdDate, COUNT(b.user.id) as numberOfBooked"
            + " from Office as o"
            + " join Map m on o.map.id = m.office.id"
            + " join Workplace w on m.id = w.map.id"
            + " left join Booking b on w.id = b.workplace.id"
            + " join City ci on o.city.id = ci.id"
            + " join Country co on co.id = ci.country.id"
            + " WHERE o.id = ?1 AND o.modifiedDate BETWEEN ?2 AND ?3"
            + " GROUP BY o.address")
    List<ReportBySingleOfficeDto> getDataForReportBySingleOffice(Long idOfOffice, Date creationDateFrom, Date creationDateTo);

    @Modifying
    @Query("")
    List<ReportByAllOfficesDto> getDataForReportByAllOffices(Date creationDateFrom, Date creationDateTo);
}
