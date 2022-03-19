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
public interface OfficeRepository  extends JpaRepository<Office, Long> {
    void deleteByCityId(Long city_id);

    List<Office> findByCityId(Long city_id);

    List<Office> findByCityName(String cityName);

    @Modifying
    @Query(value = "SELECT o.* FROM Office o" +
            " LEFT JOIN Map m on m.officeId = o.id" +
            " AND (CASE WHEN (?1 IS NOT NULL) THEN (m.kitchenNum > 0) ELSE TRUE END) = TRUE" +
            " AND (CASE WHEN (?2 IS NOT NULL) THEN (m.confRoomsNum > 0) ELSE TRUE END) = TRUE" +
            " WHERE o.cityId = ?3", nativeQuery = true)
    List<Office> findByParameters(Integer kitchenNum, Integer confNum, long cityId);

    @Modifying
    @Query(value = "select o.name as officeName, co.name as countryName, ci.name as cityName,"
            + " o.address as officeAddress, o.parking as isParking, o.createdDate as createdDate, COUNT(b.userId) as numberOfBooked"
            + " from Office as o"
            + " join Map m on o.id = m.officeId"
            + " join Workplace w on m.id = w.mapId"
            + " left join Booking b on w.id = b.workplaceId"
            + " join City ci on o.cityId = ci.id"
            + " join Country co on co.id = ci.countryId"
            + " WHERE o.id = :idOfOffice AND IF(b.startDate IS null, 1, b.startDate between :bookDateFrom AND :bookDateTo)"
            + " GROUP BY o.address", nativeQuery = true)
    List<ReportOnSingleOfficeDto> getDataForReportOnSingleOffice(@Param("idOfOffice") Long idOfOffice,
                                                                 @Param("bookDateFrom") Date bookDateFrom,
                                                                 @Param("bookDateTo") Date bookDateTo);

    @Modifying
    @Query(value = "select o.id as id, o.name as officeName, co.name as countryName, ci.name as cityName,"
            + " o.address as officeAddress, o.parking as isParking, o.createdDate as createdDate, COUNT(b.userId) as numberOfBooked"
            + " from Office o "
            + " left join Map m on o.id = m.officeId"
            + " left join Workplace w on m.id = w.mapId"
            + " left join Booking b on w.id = b.workplaceId"
            + " left join City ci on o.cityId = ci.id"
            + " left join Country co on co.id = ci.countryId"
            + " WHERE IF(b.startDate IS null, 1, b.startDate between :bookDateFrom AND :bookDateTo)"
            + " GROUP BY o.address", nativeQuery = true)
    List<ReportOnAllOfficesDto> getDataForReportOnAllOffices(@Param("bookDateFrom") Date bookDateFrom,
                                                             @Param("bookDateTo") Date bookDateTo);
}
