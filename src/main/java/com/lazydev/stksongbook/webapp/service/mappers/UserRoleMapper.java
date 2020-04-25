package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.service.dto.UserRoleDTO;
import com.lazydev.stksongbook.webapp.data.model.UserRole;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserRoleMapper {

  UserRoleDTO map(UserRole entity);
  UserRole map(UserRoleDTO dto);
}
