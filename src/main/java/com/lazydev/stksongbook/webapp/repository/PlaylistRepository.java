package com.lazydev.stksongbook.webapp.repository;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

  List<Playlist> findByIsPrivate(boolean isPrivate);
  Page<Playlist> findByIsPrivate(boolean isPrivate, Pageable req);

  Optional<Playlist> findByIdAndIsPrivate(Long id, boolean isPrivate);

  List<Playlist> findByOwnerId(Long ownerId);

  List<Playlist> findByOwnerIdAndIsPrivate(Long ownerId, boolean isPrivate);

  List<Playlist> findBySongsId(Long songId);

  List<Playlist> findBySongsIdAndIsPrivate(Long songId, boolean isPrivate);

  List<Playlist> findByNameIgnoreCaseAndIsPrivate(String name, boolean isPrivate);

  List<Playlist> findByNameIgnoreCase(String name);

  List<Playlist> findByNameContainingIgnoreCase(String name);
  Page<Playlist> findByNameContainingIgnoreCase(String name, Pageable req);

  List<Playlist> findByNameContainingIgnoreCaseAndIsPrivate(String name, boolean isPrivate);
  Page<Playlist> findByNameContainingIgnoreCaseAndIsPrivate(String name, boolean isPrivate, Pageable req);
}
