package com.lazydev.stksongbook.webapp.repository;

import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSongRatingRepository extends JpaRepository<UserSongRating, Long> {

  Optional<UserSongRating> findByUserIdAndSongId(Long userId, Long songId);
  List<UserSongRating> findBySongId(Long id);
  List<UserSongRating> findByUserId(Long id);

  List<UserSongRating> findByRating(BigDecimal rating);
  List<UserSongRating> findByRatingGreaterThanEqual(BigDecimal rating);
  List<UserSongRating> findByRatingLessThanEqual(BigDecimal rating);
}
