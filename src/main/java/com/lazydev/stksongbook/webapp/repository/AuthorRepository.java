package com.lazydev.stksongbook.webapp.repository;

import com.lazydev.stksongbook.webapp.data.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

  Optional<Author> findByName(String name);
  List<Author> findByNameIgnoreCase(String name);
  List<Author> findByNameContainingIgnoreCase(String name);
  Page<Author> findByNameContainingIgnoreCase(String name, PageRequest req);
}
