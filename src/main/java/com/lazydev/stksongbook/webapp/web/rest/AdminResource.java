package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.UserDTO;
import com.lazydev.stksongbook.webapp.service.mappers.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminResource {

  private final Logger log = LoggerFactory.getLogger(AdminResource.class);

  private final UserService userService;
  private final UserMapper userMapper;

  public AdminResource(UserService userService, UserMapper userMapper) {
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @PatchMapping("/{userId}/update-role/{roleId}")
  public ResponseEntity<UserDTO> updateUserRole(@PathVariable Long userId, @PathVariable Long roleId) {
    log.debug("Request to update user {} role to {}", userId, roleId);
    return ResponseEntity.ok(userMapper.map(userService.changeRole(userId, roleId)));
  }

  @PatchMapping("/{userId}/activate-user")
  public ResponseEntity<UserDTO> activateUser(@PathVariable Long userId) {
    log.debug("Request to activate user {}", userId);
    return ResponseEntity.ok(userMapper.map(userService.activateUser(userId)));
  }
}
