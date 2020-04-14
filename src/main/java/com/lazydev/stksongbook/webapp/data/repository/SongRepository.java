package com.lazydev.stksongbook.webapp.data.repository;

import com.lazydev.stksongbook.webapp.data.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

  @Query(
      value = "SELECT * FROM songs s WHERE s.id = (SELECT a.song_id FROM songs_authors a WHERE a.author_id = ?1)",
      nativeQuery = true)
  List<Song> findByAuthorId(@Param("authorId") Long authorId);

  List<Song> findByCategoryId(Long categoryId);

  @Query(
      value = "SELECT * FROM songs s WHERE s.id = (SELECT t.song_id FROM songs_tags t WHERE t.tag_id = ?1)",
      nativeQuery = true)
  List<Song> findByTagId(@Param("tagId") Long tagId);



  @Query(value = "SELECT * FROM songs s JOIN playlists_songs p ON (p.song_id=s.id) " +
      "JOIN playlists pp ON (pp.id=p.playlist_id AND pp.is_private = false) WHERE p.playlist_id = ?1",
      nativeQuery = true)
  Set<Song> findSongsFromPublicPlaylist(@Param("playlistId") Long id);

  @Query(value = "SELECT * FROM songs s JOIN playlists_songs p ON (p.song_id=s.id) WHERE p.playlist_id = ?1",
      nativeQuery = true)
  Set<Song> findSongsFromPlaylist(@Param("playlistId") Long id);

  @Query(value = "INSERT INTO playlists_songs VALUES(?1, ?2)", nativeQuery = true)
  void addSongToPlaylist(@Param("playlistId") Long playlistId, @Param("songId") Long songId);

  @Query(value = "DELETE FROM playlists_songs WHERE playlist_id = ?1 AND song_id = ?2", nativeQuery = true)
  void deleteSongFromPlaylist(@Param("playlistId") Long playlistId, @Param("songId") Long songId);
}
