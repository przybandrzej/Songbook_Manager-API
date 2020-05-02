package com.lazydev.stksongbook.webapp.repository;

import com.lazydev.stksongbook.webapp.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  List<User> findByUserRoleId(Long id);
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);
  List<User> findByUsernameContainingIgnoreCase(String text);
  List<User> findBySongsId(Long songId);
}
