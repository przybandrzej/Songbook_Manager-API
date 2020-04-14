package com.lazydev.stksongbook.webapp.api.mappers.decorator;

import com.lazydev.stksongbook.webapp.api.dto.UserDTO;
import com.lazydev.stksongbook.webapp.api.mappers.UserMapper;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class UserMapperDecorator implements UserMapper {

  @Autowired
  @Qualifier("delegate")
  private UserMapper delegate;

  @Override
  public User userDTOToUser(UserDTO dto) {
    var user = delegate.userDTOToUser(dto);
    var role = new UserRole();
    role.setId(dto.getUserRoleId());
    user.setUserRole(role);
    return user;
  }
}
