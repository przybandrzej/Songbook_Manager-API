package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import com.lazydev.stksongbook.webapp.service.UserSongRatingService;
import com.lazydev.stksongbook.webapp.service.dto.UserSongRatingDTO;
import com.lazydev.stksongbook.webapp.service.mappers.UserSongRatingMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ratings")
@AllArgsConstructor
public class UserSongRatingResource {

  private final UserSongRatingMapper mapper;
  private final UserSongRatingService service;

  @GetMapping("/{id}")
  public ResponseEntity<UserSongRatingDTO> getRatingById(
      @PathVariable("id") Long id) {
    var tmp = service.findById(id);
    return new ResponseEntity<>(mapper.map(tmp), HttpStatus.OK);
  }

  @GetMapping("/{userId}/{songId}")
  public ResponseEntity<UserSongRatingDTO> getRatingByUserIdAndSongId(
      @PathVariable("userId") Long userId, @PathVariable("songId") Long songId) {
    var tmp = service.findByUserIdAndSongId(userId, songId);
    return new ResponseEntity<>(mapper.map(tmp), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<UserSongRatingDTO>> getAllRatings(
      @RequestParam(value = "greaterThanEqual", required = false) BigDecimal greaterValue,
      @RequestParam(value = "lessThanEqual", required = false) BigDecimal lessValue,
      @RequestParam(value = "equal", required = false) BigDecimal value) {
    List<UserSongRating> list;
    if(greaterValue != null) {
      list = service.findByRatingGreaterThanEqual(greaterValue);
    } else if(lessValue != null) {
      list = service.findByRatingLessThanEqual(lessValue);
    } else if(value != null) {
      list = service.findByRating(value);
    } else {
      list = service.findAll();
    }
    List<UserSongRatingDTO> dtos = list.stream()
        .map(mapper::map)
        .collect(Collectors.toList());
    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<UserSongRatingDTO> createRating(@RequestBody @Valid UserSongRatingDTO dto) {
    var saved = service.create(dto);
    return new ResponseEntity<>(mapper.map(saved), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<UserSongRatingDTO> updateRating(@RequestBody @Valid UserSongRatingDTO dto) {
    var saved = service.update(dto);
    return new ResponseEntity<>(mapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
