package com.lazydev.stksongbook.webapp.repository;

import com.lazydev.stksongbook.webapp.data.model.SongCoauthor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongCoauthorRepository extends JpaRepository<SongCoauthor, Long> {

  List<SongCoauthor> findBySongId(Long id);
  List<SongCoauthor> findByAuthorId(Long id);
  List<SongCoauthor> findByFunctionIgnoreCase(String function);
}
