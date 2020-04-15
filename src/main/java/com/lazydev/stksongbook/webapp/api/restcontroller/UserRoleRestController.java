package com.lazydev.stksongbook.webapp.api.restcontroller;

import com.lazydev.stksongbook.webapp.api.dto.UserDTO;
import com.lazydev.stksongbook.webapp.api.dto.UserRoleDTO;
import com.lazydev.stksongbook.webapp.api.mappers.UserRoleMapper;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.data.service.UserRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/user_roles")
public class UserRoleRestController {

  private UserRoleService service;
  private UserRoleMapper modelMapper;

  public UserRoleRestController(UserRoleService service, UserRoleMapper mapper) {
    this.service = service;
    this.modelMapper = mapper;
  }

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

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserRoleDTO create(@RequestBody UserRoleDTO obj) {
    return convertToDto(service.save(convertToEntity(obj)));
  }

  @PutMapping("/id/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void update(@RequestBody UserRoleDTO obj) {
    service.save(convertToEntity(obj));
  }

  @DeleteMapping("/id/{id}")
  public void delete(@PathVariable("id") Long id) {
    service.deleteById(id);
  }

  public UserRoleDTO convertToDto(UserRole userRole) {
    return modelMapper.userRoleToUserRoleDTO(userRole);
  }

  public UserRole convertToEntity(UserRoleDTO userRoleDto) {
    return modelMapper.userRoleDTOToUserRole(userRoleDto);
  }
}
