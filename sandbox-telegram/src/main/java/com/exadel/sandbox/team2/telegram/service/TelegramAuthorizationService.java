package com.exadel.sandbox.team2.telegram.service;

import com.exadel.sandbox.team2.serivce.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TelegramAuthorizationService {

  private final UserService userService;

  public TelegramAuthorizationService(UserService userService) {
    this.userService = userService;
  }
}
