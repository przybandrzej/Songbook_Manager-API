package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.security.UserContextService;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.UserDTO;
import com.lazydev.stksongbook.webapp.service.exception.EmailAlreadyUsedException;
import com.lazydev.stksongbook.webapp.service.exception.ForbiddenOperationException;
import com.lazydev.stksongbook.webapp.service.exception.UsernameAlreadyUsedException;
import com.lazydev.stksongbook.webapp.service.mappers.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminResource {

  private final Logger log = LoggerFactory.getLogger(AdminResource.class);

  @Value("${spring.flyway.placeholders.role.superuser}")
  private String superuserRoleName;
  @Value("${spring.flyway.placeholders.role.admin}")
  private String adminRoleName;

  private final UserContextService userContextService;
  private final UserService userService;
  private final UserMapper userMapper;

  public AdminResource(UserContextService userContextService, UserService userService, UserMapper userMapper) {
    this.userContextService = userContextService;
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @PutMapping("/update-user")
  public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO) {
    if(!(userContextService.getCurrentUser().getUserRole().getName().equals(superuserRoleName)
        || userContextService.getCurrentUser().getUserRole().getName().equals(adminRoleName))) {
      throw new ForbiddenOperationException("No permission.");
    }
    User found = userService.findById(userDTO.getId());
    User user = userMapper.map(userDTO);
    user.setUsername(found.getUsername());
    user.setEmail(found.getEmail());
    User saved = userService.save(user);
    return ResponseEntity.ok(userMapper.map(saved));
  }

  @PatchMapping("/update-role/{userId}/{roleId}")
  public ResponseEntity<UserDTO> updateUserRole(@PathVariable Long userId, @PathVariable Long roleId) {
    return ResponseEntity.ok(userMapper.map(userService.changeRole(userId, roleId)));
  }

  @PatchMapping("/activate-user/{userId}")
  public ResponseEntity<UserDTO> activateUser(@PathVariable Long userId) {
    return ResponseEntity.ok(userMapper.map(userService.activateUser(userId)));
  }
}
