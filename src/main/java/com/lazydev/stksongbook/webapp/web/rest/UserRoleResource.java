package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.service.UserRoleService;
import com.lazydev.stksongbook.webapp.service.dto.UserDTO;
import com.lazydev.stksongbook.webapp.service.dto.UserRoleDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.mappers.UserMapper;
import com.lazydev.stksongbook.webapp.service.mappers.UserRoleMapper;
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

  @GetMapping("/{id}")
  public ResponseEntity<UserRoleDTO> getById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(mapper.map(service.findById(id)), HttpStatus.OK);
  }

  @GetMapping("/name/{searchQuery}")
  public ResponseEntity<List<UserRoleDTO>> getByNameSearchQuery(@PathVariable("searchQuery") String searchQuery) {
    List<UserRoleDTO> list = service.findByNameFragment(searchQuery).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}/users")
  public ResponseEntity<List<UserDTO>> getUserRoleUsers(@PathVariable("id") Long id) {
    var tmp = service.findById(id);
    List<UserDTO> list = tmp.getUsers().stream().map(userMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<UserRoleDTO> createRole(@RequestBody @Valid UniversalCreateDTO userRoleDto) {
    var saved = service.create(userRoleDto);
    return new ResponseEntity<>(mapper.map(saved), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<UserRoleDTO> updateRole(@RequestBody @Valid UserRoleDTO userRoleDto) {
    var saved = service.update(userRoleDto);
    return new ResponseEntity<>(mapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
