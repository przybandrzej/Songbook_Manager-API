package com.lazydev.stksongbook.webapp.repository;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

  List<Playlist> findByIsPrivate(boolean isPrivate);

  Optional<Playlist> findByIdAndIsPrivate(Long id, boolean isPrivate);

  List<Playlist> findByOwnerIdAndIsPrivate(Long ownerId, boolean isPrivate);

  List<Playlist> findByNameIgnoreCaseAndIsPrivate(String name, boolean isPrivate);

  List<Playlist> findByNameContainsIgnoreCaseAndIsPrivate(String name, boolean isPrivate);
}
