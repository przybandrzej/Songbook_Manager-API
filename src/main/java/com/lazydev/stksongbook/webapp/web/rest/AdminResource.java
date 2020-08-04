package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.config.ApplicationProperties;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.service.UserRoleService;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.UserDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.AddUserDTO;
import com.lazydev.stksongbook.webapp.service.exception.EmailAlreadyUsedException;
import com.lazydev.stksongbook.webapp.service.exception.SuperUserAlreadyExistsException;
import com.lazydev.stksongbook.webapp.service.exception.UsernameAlreadyUsedException;
import com.lazydev.stksongbook.webapp.service.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminResource {

  private final UserMapper mapper;
  private final UserService service;
  private final UserRoleService roleService;
  private final ApplicationProperties applicationProperties;
  private final PasswordEncoder passwordEncoder;

  @GetMapping("/id/{id}")
  public ResponseEntity<UserDTO> getById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(mapper.map(service.findById(id)), HttpStatus.OK);
  }

  @PostMapping("/user")
  public ResponseEntity<UserDTO> createUser(@RequestBody @Valid AddUserDTO form) {
    if(service.findByEmailNoException(form.getEmail()).isPresent()) {
      throw new EmailAlreadyUsedException();
    }
    if(service.findByUsernameNoException(form.getUsername()).isPresent()) {
      throw new UsernameAlreadyUsedException(form.getUsername());
    }
    UserRole role = roleService.findById(form.getRoleId());
    if(role.getName().equals(applicationProperties.getRole().getSuperuser()) && !role.getUsers().isEmpty()) {
      throw new SuperUserAlreadyExistsException();
    }
    User user = mapper.mapFromAddUserDto(form);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User created = service.save(user);
    return new ResponseEntity<>(mapper.map(created), HttpStatus.CREATED);
  }

  @DeleteMapping("/id/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
