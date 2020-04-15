package com.lazydev.stksongbook.webapp.data.repository;

import com.lazydev.stksongbook.webapp.data.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

  List<Song> findByTitle(String title);
  List<Song> findByTitleContaining(String text);
  List<Song> findByLyricsContaining(String text);
  List<Song> findByCategoryId(Long categoryId);
  List<Song> findByTagsId(Long tagId);
  List<Song> findByAuthorsIdAuthorId(Long authorId);
  List<Song> findByPlaylistsId(Long authorId);
  List<Song> findByPlaylistsIdAndPlaylistsIsPrivate(Long authorId, boolean isPrivate);
  List<Song> findByRatingsRatingGreaterThanEqual(Double value);
  List<Song> findByRatingsRatingLessThanEqual(Double value);
  List<Song> findByAdditionTimeGreaterThanEqual(LocalDateTime date);
  List<Song> findByAdditionTimeLessThanEqual(LocalDateTime date);
}
