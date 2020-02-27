package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.UserRole;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserRoleMapper {

    /**
     * There is no need to add the Mappings because the fields' names are the same
     */

    //UserRoleMapper INSTANCE = Mappers.getMapper(UserRoleMapper.class);

    UserRoleDTO userRoleToUserRoleDTO(UserRole entity);
    UserRole userRoleDTOToUserRole(UserRoleDTO dto);
}
