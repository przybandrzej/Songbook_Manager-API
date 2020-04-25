package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.UserRoleDTO;
import com.lazydev.stksongbook.webapp.service.mappers.UserRoleMapper;
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
}
