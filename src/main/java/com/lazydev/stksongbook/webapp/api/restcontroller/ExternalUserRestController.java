package com.lazydev.stksongbook.webapp.api.restcontroller;

import com.lazydev.stksongbook.webapp.api.dto.UserDTO;
import com.lazydev.stksongbook.webapp.api.mappers.UserMapper;
import com.lazydev.stksongbook.webapp.data.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
@AllArgsConstructor
public class ExternalUserRestController {

  private UserMapper modelMapper;
  private UserService userService;

  @GetMapping("/user_roles/id/{id}/users")
  public List<UserDTO> getRoleUsers(@PathVariable("id") Long id) {
    return userService.findByUserRole(id).stream().map(modelMapper::userToUserDTO).collect(Collectors.toList());
  }
}
