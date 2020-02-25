package com.lazydev.stksongbook.webapp.restcontroller;

import com.lazydev.stksongbook.webapp.dto.UserRoleDTO;
import com.lazydev.stksongbook.webapp.dto.UserRoleMapper;
import com.lazydev.stksongbook.webapp.model.UserRole;
import com.lazydev.stksongbook.webapp.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user_roles")
public class UserRoleRestController {

    @Autowired
    private UserRoleService service;
    
    @Autowired
    private UserRoleMapper modelMapper;

    @GetMapping
    public List<UserRoleDTO> getAll(){
        List<UserRole> list = (List<UserRole>) service.findAll();;
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public UserRoleDTO getById(@PathVariable("id") Long id) {
        Optional<UserRole> userOpt = service.findById(id);
        return userOpt.map(this::convertToDto).orElse(null);
    }

    @GetMapping("/name/{name}")
    public List<UserRoleDTO> getByName(@PathVariable("name") String name){
        List<UserRole> list = (List<UserRole>) service.findByName(name);;
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
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
