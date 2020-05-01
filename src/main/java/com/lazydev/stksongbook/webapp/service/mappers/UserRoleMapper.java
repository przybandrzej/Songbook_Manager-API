package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.service.dto.UserRoleDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.mappers.decorator.UserRoleMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(UserRoleMapperDecorator.class)
public interface UserRoleMapper {

  UserRoleDTO map(UserRole entity);

  @Mapping(target = "users", ignore = true)
  UserRole map(UserRoleDTO dto);

  @Mapping(target = "users", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "name", source = "name")
  UserRole map(UniversalCreateDTO dto);
}
