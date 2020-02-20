package com.lazydev.stksongbook.webapp.dao;

import com.lazydev.stksongbook.webapp.model.Playlist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistDAO extends CrudRepository<Playlist, Long> {
}
