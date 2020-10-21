package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.GuitarCord;
import com.lazydev.stksongbook.webapp.service.dto.GuitarCordDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface GuitarCordMapper {

  GuitarCordDTO map(GuitarCord entity);
}
