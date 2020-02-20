package com.lazydev.stksongbook.webapp.dao;

import com.lazydev.stksongbook.webapp.model.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongDAO extends CrudRepository<Song, Long> {
}
