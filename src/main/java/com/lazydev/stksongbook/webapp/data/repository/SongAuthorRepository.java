package com.lazydev.stksongbook.webapp.data.repository;

import com.lazydev.stksongbook.webapp.data.model.SongAuthor;
import org.springframework.data.repository.CrudRepository;

public interface SongAuthorRepository extends CrudRepository<SongAuthor, Long> {
}
