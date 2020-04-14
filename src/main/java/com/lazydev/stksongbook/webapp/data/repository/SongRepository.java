package com.lazydev.stksongbook.webapp.data.repository;

import com.lazydev.stksongbook.webapp.data.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

  @Query(
      value = "SELECT * FROM songs s WHERE s.id = (SELECT a.song_id FROM songs_authors a WHERE a.author_id = ?1)",
      nativeQuery = true)
  Iterable<Song> findByAuthorId(@Param("authorId") Long authorId);

  Iterable<Song> findByCategoryId(Long categoryId);

  @Query(
      value = "SELECT * FROM songs s WHERE s.id = (SELECT t.song_id FROM songs_tags t WHERE t.tag_id = ?1)",
      nativeQuery = true)
  Iterable<Song> findByTagId(@Param("tagId") Long tagId);
}
