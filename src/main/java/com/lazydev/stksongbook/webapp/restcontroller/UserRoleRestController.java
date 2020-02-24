package com.lazydev.stksongbook.webapp.restcontroller;

import com.lazydev.stksongbook.webapp.dto.UserRoleDTO;
import com.lazydev.stksongbook.webapp.model.UserRole;
import com.lazydev.stksongbook.webapp.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    @GetMapping
    public List<UserRoleDTO> getAll(){
        List<UserRole> list = (List<UserRole>) service.findAll();;
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public UserRoleDTO getById(@PathVariable("id") Long id) {
        Optional<UserRole> userOpt = service.findById(id);
        if(userOpt.isPresent()) return convertToDto(userOpt.get());
        else return null;
    }

    @GetMapping("/name/{name}")
    public List<UserRoleDTO> getByName(@PathVariable("name") String name){
        List<UserRole> list = (List<UserRole>) service.findByName(name);;
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRole addUserRole(@RequestBody UserRole obj) {
        return service.save(obj);
    }

    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserRole updateUserRole(@RequestBody UserRole obj) {
        return service.save(obj);
    }*/

    @DeleteMapping("/id/{id}")
    public void deleteUserRole(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    public UserRoleDTO convertToDto(UserRole userRole) {
        /*PropertyMap<UserRole, UserRoleDTO> personMap = new PropertyMap<UserRole, UserRoleDTO>() {
            protected void configure() {
                map().setUserRoleRole(source.getUserRoleRole().getName());
                if (userRole.getFirstName() != null) { map().setFirstName(source.getFirstName()); }
                else { map().setFirstName(""); }
                if (userRole.getLastName() != null) { map().setLastName(source.getLastName()); }
                else { map().setLastName(""); }
            }
        };
        modelMapper.addMappings(personMap);*/
        UserRoleDTO userRoleDto = modelMapper.map(userRole, UserRoleDTO.class);
        return userRoleDto;
    }

    //TODO
    public UserRole convertToEntity(UserRoleDTO userRoleDto) {
        /*PropertyMap<UserRoleDTO, UserRole> personMap = new PropertyMap<UserRoleDTO, UserRole>() {
            protected void configure() {
                map().setUserRoleRole(source.getUserRoleRole());
                if (userRole.getFirstName() != null) { map().setFirstName(source.getFirstName()); }
                else { map().setFirstName(""); }
                if (userRole.getLastName() != null) { map().setLastName(source.getLastName()); }
                else { map().setLastName(""); }
            }
        };
        modelMapper.addMappings(personMap);*/
        UserRole userRole = modelMapper.map(userRoleDto, UserRole.class);
        return userRole;
    }
}
