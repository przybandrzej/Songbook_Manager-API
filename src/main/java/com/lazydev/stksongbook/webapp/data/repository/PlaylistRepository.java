package com.lazydev.stksongbook.webapp.data.repository;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

  @Query(value = "SELECT * FROM playlists p WHERE p.is_private = false ORDER BY p.id", nativeQuery = true)
  List<Playlist> findAllPublic();

  @Query(value = "SELECT * FROM playlists p WHERE p.is_private = false AND p.id = ?1 ORDER BY p.id", nativeQuery = true)
  Optional<Playlist> findPublicById(@Param("id") Long id);

  @Query(value = "SELECT * FROM playlists p WHERE p.owner = ?1 ORDER BY p.id", nativeQuery = true)
  List<Playlist> findByOwner(@Param("ownerId") Long ownerId);

  @Query(value = "SELECT * FROM playlists p WHERE p.owner = ?1 AND p.is_private = false ORDER BY p.id",
  nativeQuery = true)
  List<Playlist> findPublicByOwner(@Param("ownerId") Long ownerId);

  @Query(value = "SELECT * FROM playlists p WHERE p.is_private = false AND p.name = ?1 ORDER BY p.id",
      nativeQuery = true)
  List<Playlist> findPublicByName(@Param("name") String name);

  List<Playlist> findAllByName(@Param("name") String name);
}
