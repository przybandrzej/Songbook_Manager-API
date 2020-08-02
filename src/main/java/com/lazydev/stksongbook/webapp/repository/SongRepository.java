package com.lazydev.stksongbook.webapp.repository;

import com.lazydev.stksongbook.webapp.data.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

  Optional<Song> findByIdAndIsAwaiting(Long id, boolean awaiting);

  List<Song> findByIsAwaiting(boolean isAwaiting);
  Page<Song> findByIsAwaiting(boolean isAwaiting, Pageable req);

  List<Song> findByTitleIgnoreCase(String title);
  List<Song> findByTitleIgnoreCaseAndIsAwaiting(String title, boolean isAwaiting);

  List<Song> findByTitleContainingIgnoreCase(String text);
  Page<Song> findByTitleContainingIgnoreCase(String text, Pageable req);
  List<Song> findByTitleContainingIgnoreCaseAndIsAwaiting(String text, boolean isAwaiting);
  Page<Song> findByTitleContainingIgnoreCaseAndIsAwaiting(String text, boolean isAwaiting, Pageable req);

  List<Song> findByLyricsContainingIgnoreCase(String text);
  Page<Song> findByLyricsContainingIgnoreCase(String text, Pageable req);
  List<Song> findByLyricsContainingIgnoreCaseAndIsAwaiting(String text, boolean isAwaiting);
  Page<Song> findByLyricsContainingIgnoreCaseAndIsAwaiting(String text, boolean isAwaiting, Pageable req);

  List<Song> findByCategoryId(Long categoryId);
  Page<Song> findByCategoryId(Long categoryId, Pageable req);
  List<Song> findByCategoryIdAndIsAwaiting(Long categoryId, boolean isAwaiting);
  Page<Song> findByCategoryIdAndIsAwaiting(Long categoryId, boolean isAwaiting, Pageable req);

  List<Song> findByTagsId(Long tagId);
  List<Song> findByTagsIdAndIsAwaiting(Long tagId, boolean isAwaiting);
  Page<Song> findByTagsIdAndIsAwaiting(Long tagId, boolean isAwaiting, Pageable req);

  List<Song> findByAuthorId(Long authorId);
  Page<Song> findByAuthorId(Long authorId, Pageable req);
  List<Song> findByAuthorIdAndIsAwaiting(Long authorId, boolean isAwaiting);
  Page<Song> findByAuthorIdAndIsAwaiting(Long authorId, boolean isAwaiting, Pageable req);

  List<Song> findByPlaylistsIdAndIsAwaitingAndPlaylistsIsPrivate(Long authorId, boolean isAwaiting, boolean isPrivate);

  List<Song> findByRatingsAndIsAwaiting(Double value, boolean isAwaiting);
  List<Song> findByRatingsRatingGreaterThanEqualAndIsAwaiting(Double value, boolean isAwaiting);
  List<Song> findByRatingsRatingLessThanEqualAndIsAwaiting(Double value, boolean isAwaiting);

  List<Song> findByAdditionsTimestampGreaterThanEqual(LocalDateTime date);
  List<Song> findByAdditionsTimestampLessThanEqual(LocalDateTime date);
  List<Song> findByAdditionsTimestampGreaterThanEqualAndIsAwaiting(LocalDateTime date, boolean isAwaiting);
  List<Song> findByAdditionsTimestampLessThanEqualAndIsAwaiting(LocalDateTime date, boolean isAwaiting);

  List<Song> findByUsersSongsIdAndIsAwaiting(Long userId, boolean isAwaiting);
}
