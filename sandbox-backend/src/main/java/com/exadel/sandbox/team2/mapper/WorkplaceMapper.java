package com.exadel.sandbox.team2.mapper;

import com.exadel.sandbox.team2.domain.Workplace;
import com.exadel.sandbox.team2.dto.WorkplaceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkplaceMapper {
    WorkplaceDto toDto(Workplace workplace);
    Workplace toEntity(WorkplaceDto workplaceDto);
}
