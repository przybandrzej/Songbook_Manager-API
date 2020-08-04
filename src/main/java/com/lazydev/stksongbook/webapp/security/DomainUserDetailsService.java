package com.lazydev.stksongbook.webapp.security;


import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.repository.UserRepository;
import com.lazydev.stksongbook.webapp.service.exception.UserNotExistsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

  private UserRepository userRepository;

  public DomainUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    Optional<User> userByEmail = userRepository.findByEmail(login);
    if(userByEmail.isPresent()) {
      return userByEmail.map(this::createSpringSecurityUser).orElseThrow(() -> new UserNotExistsException(login));
    } else {
      Optional<User> userByUsername = userRepository.findByUsername(login);
      return userByUsername.map(this::createSpringSecurityUser).orElseThrow(() -> new UserNotExistsException(login));
    }
  }

  // todo
  private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user) {
    return null;
  }
}
