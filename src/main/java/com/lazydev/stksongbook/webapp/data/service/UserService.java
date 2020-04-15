package com.lazydev.stksongbook.webapp.data.service;

import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository repository;

  public Optional<User> findById(Long id) {
    return repository.findById(id);
  }

  public Iterable<User> findByUsername(String name) {
    List<User> list = new ArrayList<>();
    for(User element : repository.findAll()) {
      if(element.getUsername().equals(name)) list.add(element);
    }
    return list;
  }

  public List<User> findByUserRole(Long id) {
    return repository.findByUserRoleId(id);
  }

  public Iterable<User> findAll() {
    return repository.findAll();
  }

  public User save(User saveUser) {
    return repository.save(saveUser);
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }
}
