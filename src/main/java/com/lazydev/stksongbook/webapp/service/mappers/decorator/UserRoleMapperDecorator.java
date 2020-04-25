package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.UserRoleDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.mappers.UserRoleMapper;
import com.lazydev.stksongbook.webapp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;

public abstract class UserRoleMapperDecorator implements UserRoleMapper {

  @Autowired
  @Qualifier("delegate")
  private UserRoleMapper delegate;
  @Autowired
  private UserService userService;

  @Override
  public UserRole map(UserRoleDTO dto) {
    var userRole = delegate.map(dto);
    userRole.setUsers(new HashSet<>(userService.findByUserRole(dto.getId())));
    return userRole;
  }

  @Override
  public UserRole map(UniversalCreateDTO dto) {
    var role = delegate.map(dto);
    role.setUsers(new HashSet<>());
    role.setId(Constants.DEFAULT_ID);
    return role;
  }
}
