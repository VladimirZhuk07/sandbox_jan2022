package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);

    @Modifying
    @Query("update Booking b set b.active = false where b.user.id = ?1")
    void updateByUserId(Long userId);
}
