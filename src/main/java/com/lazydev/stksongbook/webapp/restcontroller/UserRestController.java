package com.lazydev.stksongbook.webapp.restcontroller;

import com.lazydev.stksongbook.webapp.dto.UserDTO;
import com.lazydev.stksongbook.webapp.dto.UserMapper;
import com.lazydev.stksongbook.webapp.model.User;
import com.lazydev.stksongbook.webapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserMapper modelMapper;

    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseBody
    public List<UserDTO> getAll() {
        List<User> users = (List<User>) userService.findAll();
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public UserDTO getById(@PathVariable("id") Long id) {
        Optional<User> userOpt = userService.findById(id);
        return userOpt.map(this::convertToDto).orElse(null);
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

    public UserDTO convertToDto(User user) {
        return modelMapper.userToUserDTO(user);
    }

    public User convertToEntity(UserDTO userDto) {
        return modelMapper.userDTOToUser(userDto);
    }
}
