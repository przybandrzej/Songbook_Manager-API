package com.lazydev.stksongbook.webapp.security;

import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.repository.UserRepository;
import com.lazydev.stksongbook.webapp.service.exception.NotAuthenticatedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserContextService {

  private final UserRepository repository;

  public User getCurrentUser() {
    return SecurityUtils.getCurrentUserLogin().map(login ->
        repository.findByUsername(login).or(() -> repository.findByEmail(login)).orElseThrow(NotAuthenticatedException::new))
        .orElseThrow(NotAuthenticatedException::new);
  }
}
