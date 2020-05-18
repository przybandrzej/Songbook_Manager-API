package com.lazydev.stksongbook.webapp.repository;

import com.lazydev.stksongbook.webapp.data.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

  List<Song> findByTitleIgnoreCase(String title);

  List<Song> findByTitleContainingIgnoreCase(String text);
  Page<Song> findByTitleContainingIgnoreCase(String text, Pageable req);

  List<Song> findByLyricsContainingIgnoreCase(String text);
  Page<Song> findByLyricsContainingIgnoreCase(String text, Pageable req);

  List<Song> findByCategoryId(Long categoryId);
  Page<Song> findByCategoryId(Long categoryId, Pageable req);

  List<Song> findByTagsId(Long tagId);

  List<Song> findByAuthorId(Long authorId);
  Page<Song> findByAuthorId(Long authorId, Pageable req);

  List<Song> findByPlaylistsIdAndPlaylistsIsPrivate(Long authorId, boolean isPrivate);

  List<Song> findByRatings(Double value);
  List<Song> findByRatingsRatingGreaterThanEqual(Double value);
  List<Song> findByRatingsRatingLessThanEqual(Double value);

  List<Song> findByCreationTimeGreaterThanEqual(LocalDateTime date);
  List<Song> findByCreationTimeLessThanEqual(LocalDateTime date);

  List<Song> findByUsersSongsId(Long userId);
}
