package com.exadel.sandbox.team2.mapper;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDto entityToDto(User user);
  User DtoToEntity(UserDto user);
}
