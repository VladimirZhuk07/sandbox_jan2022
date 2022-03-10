package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.UserState;
import com.exadel.sandbox.team2.dto.UsersPOJO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
    @Query("SELECT u.id, u.firstName, u.lastName, b.startDate as timeStart, b.endDate as timeEnd, b.workplace.id as workplaceID" +
            " FROM User u left join Booking as b ON u.id = b.user.id")
    List<UsersPOJO> dayMneResultat();
}
