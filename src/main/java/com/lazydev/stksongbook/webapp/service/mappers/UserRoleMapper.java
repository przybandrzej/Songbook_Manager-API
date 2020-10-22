package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.service.dto.UserRoleDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserRoleMapper {

  UserRoleDTO map(UserRole entity);
}
