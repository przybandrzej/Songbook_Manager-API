package com.lazydev.stksongbook.webapp.repository;

import com.lazydev.stksongbook.webapp.data.model.SongAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongAuthorRepository extends JpaRepository<SongAuthor, Long> {

  List<SongAuthor> findBySongId(Long id);
  List<SongAuthor> findByAuthorId(Long id);
  List<SongAuthor> findByFunctionIgnoreCase(String function);
}
