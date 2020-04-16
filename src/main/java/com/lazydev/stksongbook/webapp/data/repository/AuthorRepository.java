package com.lazydev.stksongbook.webapp.data.repository;

import com.lazydev.stksongbook.webapp.data.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

  List<Author> findByNameIgnoreCase(String name);
}
