package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.domain.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository  extends JpaRepository<Map, Long> {

    @Modifying
    @Query("delete from Map m where m.officeId = ?1")
    void deleteByOfficeId(Office office);

}
