package com.lazydev.stksongbook.webapp.repository;

import com.lazydev.stksongbook.webapp.data.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

  List<Tag> findByNameContainingIgnoreCase(String name);

  Optional<Tag> findByName(String name);
}
