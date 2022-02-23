package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByTelegramAuthorizationCode(String authorizationCode);
    Optional<User> findByChatIdOrPhoneNumber(String chatId,String phone);
}
