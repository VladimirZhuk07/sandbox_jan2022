package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.Workplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkplaceRepository extends JpaRepository<Workplace, Long> {

    List<Workplace> findByMapId(Long id);

    @Modifying
    @Query(value = "SELECT w.* FROM Workplace w" +
            " LEFT JOIN Booking b ON b.workplaceId = w.id" +
            " AND b.startDate <= ?3" +
            " AND b.endDate > ?2" +
            " AND b.active = true" +
            " AND (CASE WHEN (?11 = true) THEN (" +
            "   (CASE WHEN (?4 = true) THEN (b.monday) ELSE true END) = true" +
            "   AND (CASE WHEN (?5 = true) THEN (b.tuesday) ELSE true END = true)" +
            "   AND (CASE WHEN (?6 = true) THEN (b.wednesday) ELSE true END) = true" +
            "   AND (CASE WHEN (?7 = true) THEN (b.thursday) ELSE true END) = true" +
            "   AND (CASE WHEN (?8 = true) THEN (b.friday) ELSE true END) = true" +
            "   AND (CASE WHEN (?9 = true) THEN (b.saturday) ELSE true END) = true" +
            "   AND (CASE WHEN (?10 = true) THEN (b.sunday) ELSE true END) = true" +
            " ) ELSE (true = true) END)" +
            " WHERE b.id IS NULL AND w.mapId = ?1" +
            " AND CASE WHEN (?12 IS NOT NULL) THEN (" +
            "   (CASE WHEN(?12 = true) THEN (w.nextToWindow = TRUE) ELSE (w.nextToWindow = FALSE) END)) ELSE (TRUE = TRUE) END" +
            " AND CASE WHEN (?13 IS NOT NULL) THEN (" +
            "   (CASE WHEN(?13 = true) THEN (w.pc = TRUE) ELSE (w.pc = FALSE) END)) ELSE (TRUE = TRUE) END" +
            " AND CASE WHEN (?14 IS NOT NULL) THEN (" +
            "   (CASE WHEN(?14 = true) THEN (w.monitor = TRUE) ELSE (w.monitor = FALSE) END)) ELSE (TRUE = TRUE) END" +
            " AND CASE WHEN (?15 IS NOT NULL) THEN (" +
            "   (CASE WHEN(?15 = true) THEN (w.keyboard = TRUE) ELSE (w.keyboard = FALSE) END)) ELSE (TRUE = TRUE) END" +
            " AND CASE WHEN (?16 IS NOT NULL) THEN (" +
            "   (CASE WHEN(?16 = true) THEN (w.mouse = TRUE) ELSE (w.mouse = FALSE) END)) ELSE (TRUE = TRUE) END" +
            " AND CASE WHEN (?17 IS NOT NULL) THEN (" +
            "   (CASE WHEN(?17 = true) THEN (w.headset = TRUE) ELSE (w.headset = FALSE) END)) ELSE (TRUE = TRUE) END", nativeQuery = true)
    List<Workplace> findByMapIdAndNotStartDate1(Long mapId, LocalDate startDate, LocalDate endDate,
                                               Boolean monday, Boolean tuesday, Boolean wednesday,
                                               Boolean thursday, Boolean friday, Boolean saturday,
                                               Boolean sunday, Boolean isRecurring, Boolean nextToWindows,
                                               Boolean pc, Boolean monitor, Boolean keyboard, Boolean mouse,
                                               Boolean headset);
}
