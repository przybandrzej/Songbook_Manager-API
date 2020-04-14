package com.lazydev.stksongbook.webapp.data.repository;

import com.lazydev.stksongbook.webapp.data.model.SongAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongAuthorRepository extends JpaRepository<SongAuthor, Long> {
}
