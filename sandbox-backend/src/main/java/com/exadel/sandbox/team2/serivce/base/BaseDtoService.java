package com.exadel.sandbox.team2.serivce.base;

import com.exadel.sandbox.team2.domain.base.BaseDto;
import com.exadel.sandbox.team2.domain.base.BaseEntity;

public interface BaseDtoService<E extends BaseEntity,D extends BaseDto> extends CRUDService<E>{

    D saveDto(D dto);

    D updateDto(D dto, long id);

    void checkAndSet(E entity,D dto);

}
