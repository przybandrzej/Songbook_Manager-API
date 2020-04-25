package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.service.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.service.dto.RegisterNewUserForm;
import com.lazydev.stksongbook.webapp.service.dto.UserDTO;
import com.lazydev.stksongbook.webapp.service.dto.UserSongRatingDTO;
import com.lazydev.stksongbook.webapp.service.mappers.PlaylistMapper;
import com.lazydev.stksongbook.webapp.service.mappers.UserMapper;
import com.lazydev.stksongbook.webapp.service.mappers.UserSongRatingMapper;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserRestController {

    private UserMapper modelMapper;
    private UserService userService;
    private UserSongRatingMapper userSongRatingMapper;
    private PlaylistMapper playlistMapper;

    @GetMapping
    public List<UserDTO> getAll() {
        return userService.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public UserDTO getById(@PathVariable("id") Long id) {
        return userService.findById(id).map(this::convertToDto).orElse(null);
    }

    @GetMapping("/id/{id}/ratings")
    public List<UserSongRatingDTO> getRatingsByUserId(@PathVariable("id") Long id) {
        return userService.findById(id)
            .map(user -> user.getUserRatings().stream()
                .map(userSongRatingMapper::map).collect(Collectors.toList()))
            .orElse(null);
    }

    @GetMapping("/id/{id}/playlists")
    public List<PlaylistDTO> getPlaylistsByUserId(@PathVariable("id") Long id) {
        return userService.findById(id)
            .map(user -> user.getPlaylists().stream()
                .map(playlistMapper::map).collect(Collectors.toList()))
            .orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@RequestBody RegisterNewUserForm form) {
        User user = modelMapper.mapFromRegisterForm(form);
        User userCreated = userService.save(user);
        return convertToDto(userCreated);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody UserDTO userDto) {
        User user = convertToEntity(userDto);
        userService.save(user);
    }

    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
    }

    public UserDTO convertToDto(User user) {
        return modelMapper.map(user);
    }

    public User convertToEntity(UserDTO userDto) {
        return modelMapper.map(userDto);
    }
}
