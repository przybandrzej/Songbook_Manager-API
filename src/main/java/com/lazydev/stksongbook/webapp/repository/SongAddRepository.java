package com.lazydev.stksongbook.webapp.repository;

import com.lazydev.stksongbook.webapp.data.model.SongAdd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongAddRepository extends JpaRepository<SongAdd, Long> {
}
