package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.repository.UserRoleRepository;
import com.lazydev.stksongbook.webapp.service.exception.CannotDeleteEntityException;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserRoleService {

  private final UserRoleRepository repository;

  public Optional<UserRole> findByIdNoException(Long id) {
    return repository.findById(id);
  }

  public UserRole findById(Long id) {
    return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(UserRole.class, id));
  }

  public List<UserRole> findByNameFragment(String value) {
    return repository.findByNameContainingIgnoreCase(value);
  }

  public UserRole findByName(String name) {
    return repository.findByName(name)
        .orElseThrow(() -> new EntityNotFoundException(UserRole.class, name));
  }

  public Optional<UserRole> findByNameNoException(String name) {
    return repository.findByName(name);
  }

  public List<UserRole> findAll() {
    return repository.findAll();
  }

  public UserRole save(UserRole saveRole) {
    return repository.save(saveRole);
  }

  public void deleteById(Long id) {
    var role = findById(id);
    if(!role.getUsers().isEmpty()) {
      throw new CannotDeleteEntityException(UserRole.class.getSimpleName(), "There are users belonging to this role.");
    }
    repository.deleteById(id);
  }
}
