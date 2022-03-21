package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.UserState;
import com.exadel.sandbox.team2.dto.report.ReportOnEmployeesDto;
import com.exadel.sandbox.team2.dto.report.ReportOnUsersDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("select u from User u where u.isFired = true and u.status <> 'BLOCKED'")
    List<User> findAllByIsFiredTrueAndStatusNotContains();

    Optional<User> findByChatIdAndStatus(String chatId, UserState state);

    @Modifying
    @Query(value = "SELECT u.id as id, u.firstName as firstName, u.lastName as lastName,"
            + " u.createdDate as createdDate, u.email as email, o.name as officeName, co.name as countryName, COUNT(b.userId) as numberOfBooked"
            + " FROM User u "
            + " left join Booking as b ON u.id = b.userId "
            + " left join Workplace w on w.id = b.workplaceId"
            + " left join Map m on m.id = w.mapId"
            + " left join Office o on o.id = m.officeId"
            + " left join City ci on ci.id = o.cityId"
            + " left join Country co on co.id = ci.countryId"
            + " WHERE IF(b.startDate IS null, 1, b.startDate between :bookDateFrom AND :bookDateTo)"
            + " GROUP BY u.id, o.name, co.name "
            + " ORDER BY u.firstName", nativeQuery = true)
    List<ReportOnUsersDto> getDataForUsersReport(@Param("bookDateFrom") Date bookDateFrom,
                                                 @Param("bookDateTo") Date bookDateTo);

    @Modifying
    @Query(value = "select u.firstName as firstName, u.lastName as lastName, "
            + " u.createdDate as createdDate, u.createdBy as createdBy,"
            + " u.modifiedBy as modifiedBy, u.modifiedDate as modifiedDate,"
            + " role.name as roleName, u.email as email"
            + " from User u join UserRole url on u.id = url.userId"
            + " join Role role on url.roleId = role.id "
            + " where role.name NOT IN ('USER')"
            + " AND u.createdDate between :userCreateDateFrom AND :userCreateDateTo "
            + " ORDER BY role.name ASC", nativeQuery = true)
    List<ReportOnEmployeesDto> getDataForEmployeesReport(@Param("userCreateDateFrom") Date userCreateDateFrom,
                                                         @Param("userCreateDateTo") Date userCreateDateTo);


}
