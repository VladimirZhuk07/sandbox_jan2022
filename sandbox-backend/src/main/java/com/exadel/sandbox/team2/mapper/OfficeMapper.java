package com.exadel.sandbox.team2.mapper;

import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.dto.OfficeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfficeMapper {

    OfficeDto toDto(Office office);

    Office toEntity(OfficeDto officeDto);

}
