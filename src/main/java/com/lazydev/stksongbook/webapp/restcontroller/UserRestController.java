package com.lazydev.stksongbook.webapp.restcontroller;

import com.lazydev.stksongbook.webapp.dao.UserDAO;
import com.lazydev.stksongbook.webapp.dto.UserDTO;
import com.lazydev.stksongbook.webapp.model.User;
import com.lazydev.stksongbook.webapp.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Destination;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseBody
    public List<UserDTO> getAll() {
        List<User> users = (List<User>) userService.findAll();
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // TODO change null values to responses
    @GetMapping("/{id}")
    @ResponseBody
    public UserDTO getById(@PathVariable("id") Long id) {
        Optional<User> userOpt = userService.findById(id);
        if(userOpt.isPresent()) return convertToDto(userOpt.get());
        else return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserDTO create(@RequestBody UserDTO userDto) {
        User user = convertToEntity(userDto);
        User userCreated = userService.save(user);
        return convertToDto(userCreated);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody UserDTO userDto) {
        User user = convertToEntity(userDto);
        userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
    }

    private UserDTO convertToDto(User user) {
        /*PropertyMap<User, UserDTO> personMap = new PropertyMap<User, UserDTO>() {
            protected void configure() {
                map().setUserRole(source.getUserRole().getName());
                if (user.getFirstName() != null) { map().setFirstName(source.getFirstName()); }
                else { map().setFirstName(""); }
                if (user.getLastName() != null) { map().setLastName(source.getLastName()); }
                else { map().setLastName(""); }
            }
        };
        modelMapper.addMappings(personMap);*/
        UserDTO userDto = modelMapper.map(user, UserDTO.class);
        return userDto;
    }

    //TODO
    private User convertToEntity(UserDTO userDto) {
        /*PropertyMap<UserDTO, User> personMap = new PropertyMap<UserDTO, User>() {
            protected void configure() {
                map().setUserRole(source.getUserRole());
                if (user.getFirstName() != null) { map().setFirstName(source.getFirstName()); }
                else { map().setFirstName(""); }
                if (user.getLastName() != null) { map().setLastName(source.getLastName()); }
                else { map().setLastName(""); }
            }
        };
        modelMapper.addMappings(personMap);*/
        User user = modelMapper.map(userDto, User.class);
        return user;
    }
}
