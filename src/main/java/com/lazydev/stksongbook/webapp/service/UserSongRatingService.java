package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import com.lazydev.stksongbook.webapp.data.model.UsersSongsRatingsKey;
import com.lazydev.stksongbook.webapp.repository.SongRepository;
import com.lazydev.stksongbook.webapp.repository.UserRepository;
import com.lazydev.stksongbook.webapp.repository.UserSongRatingRepository;
import com.lazydev.stksongbook.webapp.security.UserContextService;
import com.lazydev.stksongbook.webapp.service.dto.UserSongRatingDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityAlreadyExistsException;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.exception.ForbiddenOperationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserSongRatingService {

  private final UserSongRatingRepository repository;
  private final UserContextService userContextService;
  private final UserRepository userRepository;
  private final SongRepository songRepository;

  public List<UserSongRating> findAll() {
    return repository.findAll();
  }

  public Optional<UserSongRating> findByUserIdAndSongIdNoException(Long userId, Long songId) {
    return repository.findByUserIdAndSongId(userId, songId);
  }

  public UserSongRating findByUserIdAndSongId(Long userId, Long songId) {
    return repository.findByUserIdAndSongId(userId, songId)
        .orElseThrow(() -> new EntityNotFoundException(UserSongRating.class, "User ID " + userId + " Song ID " + songId));
  }

  public List<UserSongRating> findBySongId(Long id) {
    return repository.findBySongId(id);
  }

  public List<UserSongRating> findByUserId(Long id) {
    return repository.findByUserId(id);
  }

  public List<UserSongRating> findByRating(BigDecimal rating) {
    return repository.findByRating(rating);
  }

  public List<UserSongRating> findByRatingGreaterThanEqual(BigDecimal rating) {
    return repository.findByRatingGreaterThanEqual(rating);
  }

  public List<UserSongRating> findByRatingLessThanEqual(BigDecimal rating) {
    return repository.findByRatingLessThanEqual(rating);
  }

  public void delete(Long userId, Long songId) {
    UserSongRating rating = repository.findByUserIdAndSongId(userId, songId)
        .orElseThrow(() -> new EntityNotFoundException(UserSongRating.class, "user: " + userId + ", song " + songId));
    if(!userId.equals(userContextService.getCurrentUser().getId())) {
      throw new ForbiddenOperationException("No permission.");
    }
    repository.delete(rating);
  }

  public UserSongRating create(UserSongRatingDTO dto) {
    if(findByUserIdAndSongIdNoException(dto.getUserId(), dto.getSongId()).isPresent()) {
      throw new EntityAlreadyExistsException(UserSongRating.class.getSimpleName());
    }
    if(!dto.getUserId().equals(userContextService.getCurrentUser().getId())) {
      throw new ForbiddenOperationException("No permission.");
    }
    UserSongRating rating = new UserSongRating();
    rating.setRating(dto.getRating());
    User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new EntityNotFoundException(User.class, dto.getUserId()));
    Song song = songRepository.findById(dto.getSongId()).orElseThrow(() -> new EntityNotFoundException(Song.class, dto.getSongId()));
    rating.setId(new UsersSongsRatingsKey());
    rating.setUser(user);
    rating.setSong(song);
    return repository.save(rating);
  }

  public UserSongRating update(UserSongRatingDTO dto) {
    UserSongRating rating = findByUserIdAndSongId(dto.getUserId(), dto.getSongId());
    if(!rating.getUser().getId().equals(userContextService.getCurrentUser().getId())) {
      throw new ForbiddenOperationException("No permission.");
    }
    rating.setRating(dto.getRating());
    return repository.save(rating);
  }

}
