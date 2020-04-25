package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.service.dto.UserDTO;
import com.lazydev.stksongbook.webapp.service.dto.UserRoleDTO;
import com.lazydev.stksongbook.webapp.service.mappers.UserMapper;
import com.lazydev.stksongbook.webapp.service.mappers.UserRoleMapper;
import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/user_roles")
@AllArgsConstructor
public class UserRoleRestController {

  private UserRoleService service;
  private UserRoleMapper modelMapper;
  private UserMapper userMapper;

  @GetMapping
  public List<UserRoleDTO> getAll() {
    return service.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @GetMapping("/id/{id}")
  public UserRoleDTO getById(@PathVariable("id") Long id) {
    return service.findById(id).map(this::convertToDto).orElse(null);
  }

  @GetMapping("/name/{name}")
  public List<UserRoleDTO> getByName(@PathVariable("name") String name) {
    return service.findByName(name).stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @GetMapping("/id/{id}/users")
  public List<UserDTO> getUsersByRoleId(@PathVariable("id") Long id) {
    return service.findById(id)
        .map(userRole -> userRole.getUsers().stream().map(userMapper::map).collect(Collectors.toList()))
        .orElse(null);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserRoleDTO create(@RequestBody UserRoleDTO obj) {
    return convertToDto(service.save(convertToEntity(obj)));
  }

  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public void update(@RequestBody UserRoleDTO obj) {
    service.save(convertToEntity(obj));
  }

  @DeleteMapping("/id/{id}")
  public void delete(@PathVariable("id") Long id) {
    service.deleteById(id);
  }

  public UserRoleDTO convertToDto(UserRole userRole) {
    return modelMapper.map(userRole);
  }

  public UserRole convertToEntity(UserRoleDTO userRoleDto) {
    return modelMapper.map(userRoleDto);
  }
}
