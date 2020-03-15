package com.lazydev.stksongbook.webapp.api.mappers;

import com.lazydev.stksongbook.webapp.api.dto.UserRoleDTO;
import com.lazydev.stksongbook.webapp.data.model.UserRole;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserRoleMapper {

  UserRoleDTO userRoleToUserRoleDTO(UserRole entity);
  UserRole userRoleDTOToUserRole(UserRoleDTO dto);
}
