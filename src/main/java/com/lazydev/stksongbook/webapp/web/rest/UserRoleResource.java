package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.service.UserRoleService;
import com.lazydev.stksongbook.webapp.service.dto.UserDTO;
import com.lazydev.stksongbook.webapp.service.dto.UserRoleDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityAlreadyExistsException;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.mappers.UserMapper;
import com.lazydev.stksongbook.webapp.service.mappers.UserRoleMapper;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user_roles")
@AllArgsConstructor
public class UserRoleResource {

  private final UserRoleService service;
  private final UserRoleMapper mapper;
  private final UserMapper userMapper;

  @GetMapping
  public ResponseEntity<List<UserRoleDTO>> getAll() {
    List<UserRoleDTO> list = service.findAll().stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<UserRoleDTO> getById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(mapper.map(service.findById(id)), HttpStatus.OK);
  }

  @GetMapping("/name/{searchQuery}")
  public ResponseEntity<List<UserRoleDTO>> getByNameSearchQuery(@PathVariable("searchQuery") String searchQuery) {
    List<UserRoleDTO> list = service.findByNameFragment(searchQuery).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  /**
   * @deprecated This should not be implemented. User should not be loaded to UserRole (Lazy Loading) or UserRole should not have User list.
   * Should use {@link UserResource#getByRole(Long, Integer)} todo
   */
  @Deprecated(since = "1.5.5", forRemoval = true)
  @GetMapping("/id/{id}/users")
  public ResponseEntity<List<UserDTO>> getUsersByUserRoleId(@PathVariable("id") Long id) {
    var tmp = service.findById(id);
    List<UserDTO> list = tmp.getUsers().stream().map(userMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<UserRoleDTO> create(@RequestBody @Valid UniversalCreateDTO userRoleDto) {
    if(service.findByNameNoException(userRoleDto.getName()).isPresent()) {
      throw new EntityAlreadyExistsException(UserRole.class.getSimpleName(), userRoleDto.getName());
    }
    var userRole = mapper.map(userRoleDto);
    userRole.setId(Constants.DEFAULT_ID);
    var saved = service.save(userRole);
    return new ResponseEntity<>(mapper.map(saved), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<UserRoleDTO> update(@RequestBody @Valid UserRoleDTO userRoleDto) {
    if(service.findByIdNoException(userRoleDto.getId()).isEmpty()) {
      throw new EntityNotFoundException(UserRole.class, userRoleDto.getId());
    }
    var userRole = mapper.map(userRoleDto);
    var saved = service.save(userRole);
    return new ResponseEntity<>(mapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/id/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
