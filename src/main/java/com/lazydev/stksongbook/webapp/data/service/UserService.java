package com.lazydev.stksongbook.webapp.data.service;

import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository repository;

  public Optional<User> findById(Long id) {
    return repository.findById(id);
  }

  public Optional<User> findByUsername(String name) {
    return repository.findByUsername(name);
  }

  public List<User> findByUsernameContains(String text) {
    return repository.findByUsernameContaining(text);
  }

  public List<User> findByUserRole(Long id) {
    return repository.findByUserRoleId(id);
  }

  public List<User> findAll() {
    return repository.findAll();
  }

  public User save(User saveUser) {
    return repository.save(saveUser);
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }
}
