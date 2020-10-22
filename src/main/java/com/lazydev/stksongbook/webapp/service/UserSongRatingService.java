package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import com.lazydev.stksongbook.webapp.repository.SongRepository;
import com.lazydev.stksongbook.webapp.repository.UserRepository;
import com.lazydev.stksongbook.webapp.repository.UserSongRatingRepository;
import com.lazydev.stksongbook.webapp.security.UserContextService;
import com.lazydev.stksongbook.webapp.service.dto.UserSongRatingDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityAlreadyExistsException;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.exception.ForbiddenOperationException;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class UserSongRatingService {

  private final UserSongRatingRepository repository;
  private final UserContextService userContextService;
  private final UserRepository userRepository;
  private final SongRepository songRepository;

  @Value("${spring.flyway.placeholders.role.superuser}")
  private String superuserRoleName;
  @Value("${spring.flyway.placeholders.role.admin}")
  private String adminRoleName;

  public UserSongRatingService(UserSongRatingRepository repository, UserContextService userContextService, UserRepository userRepository, SongRepository songRepository) {
    this.repository = repository;
    this.userContextService = userContextService;
    this.userRepository = userRepository;
    this.songRepository = songRepository;
  }

  public List<UserSongRating> findAll() {
    return repository.findAll();
  }

  public UserSongRating findById(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(UserSongRating.class, id));
  }

  public Optional<UserSongRating> findByINoException(Long id) {
    return repository.findById(id);
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

  public void delete(Long id) {
    UserSongRating rating = findById(id);
    User currentUser = userContextService.getCurrentUser();
    if(!rating.getUser().getId().equals(currentUser.getId())
        && !currentUser.getUserRole().getName().equals(superuserRoleName)
        && !currentUser.getUserRole().getName().equals(adminRoleName)) {
      throw new ForbiddenOperationException("No permission.");
    }
    repository.delete(rating);
  }

  public UserSongRating create(@Valid UserSongRatingDTO dto) {
    if(findByUserIdAndSongIdNoException(dto.getUserId(), dto.getSongId()).isPresent()) {
      throw new EntityAlreadyExistsException(UserSongRating.class.getSimpleName());
    }
    User currentUser = userContextService.getCurrentUser();
    if(!dto.getUserId().equals(currentUser.getId())
        && !currentUser.getUserRole().getName().equals(superuserRoleName)
        && !currentUser.getUserRole().getName().equals(adminRoleName)) {
      throw new ForbiddenOperationException("No permission.");
    }
    UserSongRating rating = new UserSongRating();
    rating.setRating(dto.getRating());
    User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new EntityNotFoundException(User.class, dto.getUserId()));
    Song song = songRepository.findById(dto.getSongId()).orElseThrow(() -> new EntityNotFoundException(Song.class, dto.getSongId()));
    rating.setId(Constants.DEFAULT_ID);
    rating.setRating(dto.getRating());
    user.addRating(rating);
    song.addRating(rating);
    return repository.save(rating);
  }

  public UserSongRating update(@Valid UserSongRatingDTO dto) {
    UserSongRating rating = findById(dto.getId());
    User currentUser = userContextService.getCurrentUser();
    if(!rating.getUser().getId().equals(currentUser.getId())
        && !currentUser.getUserRole().getName().equals(superuserRoleName)
        && !currentUser.getUserRole().getName().equals(adminRoleName)) {
      throw new ForbiddenOperationException("No permission.");
    }
    rating.setRating(dto.getRating());
    return repository.save(rating);
  }

  public UserSongRating create(@Valid UserSongRatingDTO dto, User user) {
    if(findByUserIdAndSongIdNoException(user.getId(), dto.getSongId()).isPresent()) {
      throw new EntityAlreadyExistsException(UserSongRating.class.getSimpleName());
    }
    User currentUser = userContextService.getCurrentUser();
    if(!user.getId().equals(currentUser.getId())
        && !currentUser.getUserRole().getName().equals(superuserRoleName)
        && !currentUser.getUserRole().getName().equals(adminRoleName)) {
      throw new ForbiddenOperationException("No permission.");
    }
    UserSongRating rating = new UserSongRating();
    rating.setRating(dto.getRating());
    Song song = songRepository.findById(dto.getSongId()).orElseThrow(() -> new EntityNotFoundException(Song.class, dto.getSongId()));
    rating.setId(Constants.DEFAULT_ID);
    rating.setRating(dto.getRating());
    user.addRating(rating);
    song.addRating(rating);
    return repository.save(rating);
  }
}
