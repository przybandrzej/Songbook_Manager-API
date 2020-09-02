package com.lazydev.stksongbook.webapp.repository;

import com.lazydev.stksongbook.webapp.data.model.SongCoauthor;
import com.lazydev.stksongbook.webapp.data.model.enumeration.CoauthorFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongCoauthorRepository extends JpaRepository<SongCoauthor, Long> {

  Optional<SongCoauthor> findBySongIdAndAuthorId(Long songId, Long authorId);
  Optional<SongCoauthor> findBySongIdAndAuthorIdAndCoauthorFunction(Long songId, Long authorId, CoauthorFunction function);
  List<SongCoauthor> findBySongId(Long id);
  List<SongCoauthor> findByAuthorId(Long id);
  List<SongCoauthor> findByCoauthorFunction(CoauthorFunction function);
}
