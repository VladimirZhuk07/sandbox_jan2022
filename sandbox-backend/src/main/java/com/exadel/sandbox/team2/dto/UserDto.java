package com.exadel.sandbox.team2.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder


public class UserDto {
  private Long id;

  private String username;
}
