package com.exadel.sandbox.team2.mapper;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User entity);
    User toEntity(UserDto dto);
    List<UserDto> toDtos(List<User> entities);
    List<User> toEntities(List<UserDto> dtos);
}
