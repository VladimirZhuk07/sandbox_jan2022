package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.UserState;
import com.exadel.sandbox.team2.dto.report.ReportByEmployeesDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByTelegramAuthorizationCodeAndStatus(String authorizationCode, UserState status);

    Optional<User> findByChatIdOrPhoneNumber(String chatId, String phone);

    List<User> findAllByStatus(UserState state);

    Optional<User> findByChatIdAndStatus(String chatId, UserState state);

    @Modifying
    @Query("SELECT u.id as id, u.firstName as firstName, u.lastName as lastName,"
            + " u.createdDate as createdDate,  b.startDate as startDate, b.endDate as endDate"
            + " FROM User u left join Booking as b ON u.id = b.user.id WHERE u.createdDate"
            + " BETWEEN ?1 AND ?2")
    List<ReportByEmployeesDto> getDataForEmployeesReport(Date userCreationDateFrom, Date userCreationDateTo);
}
