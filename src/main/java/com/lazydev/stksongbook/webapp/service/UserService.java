package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.repository.UserRepository;
import com.lazydev.stksongbook.webapp.service.exception.UserNotExistsException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository repository;

  public Optional<User> findByIdNoException(Long id) {
    return repository.findById(id);
  }

  public User findById(Long id) {
    return repository.findById(id).orElseThrow(() -> new UserNotExistsException(id));
  }

  public Optional<User> findByUsernameNoException(String name) {
    return repository.findByUsername(name);
  }

  public User findByUsername(String name) {
    return repository.findByUsername(name).orElseThrow(() -> new UserNotExistsException(name));
  }

  public Optional<User> findByEmailNoException(String email) {
    return repository.findByEmail(email);
  }

  public User findByEmail(String email) {
    return repository.findByEmail(email).orElseThrow(() -> new UserNotExistsException(email));
  }

  public List<User> findByUsernameContains(String text) {
    return repository.findByUsernameContainingIgnoreCase(text);
  }

  public List<User> findByUserRole(Long id) {
    return repository.findByUserRoleId(id);
  }

  public List<User> findBySong(Long id) {
    return repository.findBySongsId(id);
  }

  public List<User> findAll() {
    return repository.findAll();
  }

  public List<User> findLimited(int limit) {
    return repository.findAll(PageRequest.of(0, limit)).toList();
  }

  public User save(User saveUser) {
    return repository.save(saveUser);
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }
}
