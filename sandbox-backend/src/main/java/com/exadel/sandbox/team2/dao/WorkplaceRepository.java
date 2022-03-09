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
    @Query(value = "select w from Workplace w " +
            "left join Booking b on b.workplace.id = w.id " +
            "where (w.map.id = ?1 and b.startDate <> ?2) or b.workplace.id IS NULL")
    List<Workplace> findByMapIdAndNotStartDate(Long mapId, LocalDate startDate);
}
