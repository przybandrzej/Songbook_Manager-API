package com.lazydev.stksongbook.webapp.security;


import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.repository.UserRepository;
import com.lazydev.stksongbook.webapp.service.exception.UserNotExistsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public DomainUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String login) {
    Optional<User> userByEmail = userRepository.findByEmail(login);
    if(userByEmail.isPresent()) {
      return userByEmail.map(it -> createSpringSecurityUser(it, login)).orElseThrow(() -> new UserNotExistsException(login));
    } else {
      Optional<User> userByUsername = userRepository.findByUsername(login);
      return userByUsername.map(it -> createSpringSecurityUser(it, login)).orElseThrow(() -> new UserNotExistsException(login));
    }
  }

  private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user, String login) {
    if(!user.isActivated()) {
      throw new UserNotActivatedException("User " + login + " was not activated");
    }
    List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(user.getUserRole().getName()));
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
  }
}
