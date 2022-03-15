package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.dto.report.ReportOnFloorDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MapRepository extends JpaRepository<Map, Long> {
    void deleteByOfficeId(Long office_id);

    Optional<Map> findByOfficeId(Long id);

    @Modifying
    @Query(value = "SELECT w.workplaceNumber as workplaceNumber,"
            + " w.nextToWindow as nextToWindow,"
            + " w.pc as pc,"
            + " w.monitor as monitor,"
            + " w.keyboard as keyboard,"
            + " w.mouse as mouse,"
            + " w.headset as headset,"
            + " COUNT(b.userId) as numberOfBooked"
            + " FROM workplace w LEFT JOIN booking b on w.id = b.workplaceId"
            + " WHERE IF(b.startDate IS NULL, 1, b.startDate BETWEEN :dateFrom AND :dateTo)"
            + " GROUP BY w.floor, w.workplaceNumber, w.nextToWindow, w.pc, w.monitor, w.keyboard, w.mouse, w.headset"
            + " HAVING w.floor IN(:idOfFloor)", nativeQuery = true)
    List<ReportOnFloorDto> getDataForReportOnFloor(@Param("idOfFloor") Long idOfFloor,
                                                   @Param("dateFrom") Date bookedDateFrom,
                                                   @Param("dateTo") Date bookedDateTo);
}
