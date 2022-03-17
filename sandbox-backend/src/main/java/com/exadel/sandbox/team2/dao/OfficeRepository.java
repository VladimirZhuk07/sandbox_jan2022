package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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

}
