package com.lazydev.stksongbook.webapp.data.repository;

import com.lazydev.stksongbook.webapp.data.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

  List<Song> findByTitleIgnoreCase(String title);
  List<Song> findByTitleContainingIgnoreCase(String text);
  List<Song> findByLyricsContainingIgnoreCase(String text);
  List<Song> findByCategoryId(Long categoryId);
  List<Song> findByTagsId(Long tagId);
  List<Song> findByAuthorsIdAuthorId(Long authorId);
  List<Song> findByPlaylistsIdAndPlaylistsIsPrivate(Long authorId, boolean isPrivate);
  List<Song> findByRatingsRatingGreaterThanEqual(Double value);
  List<Song> findByRatingsRatingLessThanEqual(Double value);
  List<Song> findByAdditionTimeGreaterThanEqual(LocalDateTime date);
  List<Song> findByAdditionTimeLessThanEqual(LocalDateTime date);
  List<Song> findByUsersSongsId(Long userId);
}
