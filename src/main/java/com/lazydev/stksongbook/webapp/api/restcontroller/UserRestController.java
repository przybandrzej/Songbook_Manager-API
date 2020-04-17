package com.lazydev.stksongbook.webapp.api.restcontroller;

import com.lazydev.stksongbook.webapp.api.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.api.dto.UserDTO;
import com.lazydev.stksongbook.webapp.api.dto.UserSongRatingDTO;
import com.lazydev.stksongbook.webapp.api.mappers.PlaylistMapper;
import com.lazydev.stksongbook.webapp.api.mappers.UserMapper;
import com.lazydev.stksongbook.webapp.api.mappers.UserSongRatingMapper;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.service.UserService;
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
                .map(userSongRatingMapper::usersSongsRatingsEntityToUserSongRatingDTO).collect(Collectors.toList()))
            .orElse(null);
    }

    @GetMapping("/id/{id}/playlists")
    public List<PlaylistDTO> getPlaylistsByUserId(@PathVariable("id") Long id) {
        return userService.findById(id)
            .map(user -> user.getPlaylists().stream()
                .map(playlistMapper::playlistToPlaylistDTO).collect(Collectors.toList()))
            .orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody UserDTO userDto) {
        User user = convertToEntity(userDto);
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
        return modelMapper.userToUserDTO(user);
    }

    public User convertToEntity(UserDTO userDto) {
        return modelMapper.userDTOToUser(userDto);
    }
}
