package com.lazydev.stksongbook.webapp.api.restcontroller;

import com.lazydev.stksongbook.webapp.api.dto.SongDTO;
import com.lazydev.stksongbook.webapp.api.dto.UserDTO;
import com.lazydev.stksongbook.webapp.api.mappers.UserMapper;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserRestController {

    private UserMapper modelMapper;
    private UserService userService;

    public UserRestController(UserService service, UserMapper mapper) {
        this.userService = service;
        this.modelMapper = mapper;
    }

    @GetMapping
    public List<UserDTO> getAll() {
        List<User> users = (List<User>) userService.findAll();
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public UserDTO getById(@PathVariable("id") Long id) {
        Optional<User> userOpt = userService.findById(id);
        return userOpt.map(this::convertToDto).orElse(null);
    }

    @GetMapping("/id/{id}/songs")
    public List<SongDTO> getUserSongs(@PathVariable("id") Long id) {
        //Todo
        return Collections.emptyList();
    }

    @GetMapping("/id/{id}/ratings")
    public List<SongDTO> getUserRatings(@PathVariable("id") Long id) {
        //Todo
        return Collections.emptyList();
    }

    @GetMapping("/id/{id}/playlists")
    public List<SongDTO> getUserPlaylists(@PathVariable("id") Long id) {
        //Todo
        return Collections.emptyList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
