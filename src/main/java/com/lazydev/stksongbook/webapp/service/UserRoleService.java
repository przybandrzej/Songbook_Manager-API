package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.repository.UserRoleRepository;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserRoleService {

  private UserRoleRepository repository;

  public Optional<UserRole> findByIdNoException(Long id) {
    return repository.findById(id);
  }

  public UserRole findById(Long id) {
    return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(UserRole.class, id));
  }

  public List<UserRole> findByName(String name) {
    return repository.findByNameIgnoreCase(name);
  }

  public List<UserRole> findAll() {
    return repository.findAll();
  }

  public UserRole save(UserRole saveRole) {
    return repository.save(saveRole);
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }
}
