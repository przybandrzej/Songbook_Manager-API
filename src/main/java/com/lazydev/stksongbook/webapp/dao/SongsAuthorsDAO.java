package com.lazydev.stksongbook.webapp.dao;

import com.lazydev.stksongbook.webapp.model.SongsAuthorsEntity;
import org.springframework.data.repository.CrudRepository;

public interface SongsAuthorsDAO extends CrudRepository<SongsAuthorsEntity, Long> {
}
