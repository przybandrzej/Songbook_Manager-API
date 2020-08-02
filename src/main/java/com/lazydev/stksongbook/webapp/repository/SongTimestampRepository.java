package com.lazydev.stksongbook.webapp.repository;

import com.lazydev.stksongbook.webapp.data.model.SongTimestamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongTimestampRepository extends JpaRepository<SongTimestamp, Long> {
}
