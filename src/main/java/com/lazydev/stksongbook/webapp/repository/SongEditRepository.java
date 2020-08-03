package com.lazydev.stksongbook.webapp.repository;

import com.lazydev.stksongbook.webapp.data.model.SongEdit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongEditRepository extends JpaRepository<SongEdit, Long> {
}
