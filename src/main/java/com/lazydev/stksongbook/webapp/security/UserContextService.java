package com.lazydev.stksongbook.webapp.security;

import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.repository.UserRepository;
import com.lazydev.stksongbook.webapp.service.exception.NotAuthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserContextService {

  private static final Logger log = LoggerFactory.getLogger(UserContextService.class);

  @Value("${spring.flyway.placeholders.role.superuser}")
  private String superuser;

  @Value("${spring.flyway.placeholders.role.admin}")
  private String admin;

  @Value("${spring.flyway.placeholders.role.moderator}")
  private String moderator;

  @Value("${spring.flyway.placeholders.role.user}")
  private String user;

  private final UserRepository repository;

  public UserContextService(UserRepository repository) {
    this.repository = repository;
  }

  public User getCurrentUser() {
    return SecurityUtils.getCurrentUserLogin().map(login ->
        repository.findByUsername(login).or(() -> repository.findByEmail(login)).orElseThrow(NotAuthenticatedException::new))
        .orElseThrow(NotAuthenticatedException::new);
  }

  /**
   * Check if a user is authenticated.
   *
   * @return true if the user is authenticated, false otherwise
   */
  public boolean isAuthenticated() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(securityContext.getAuthentication())
        .map(authentication -> authentication.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(user)
                || grantedAuthority.getAuthority().equals(moderator)
                || grantedAuthority.getAuthority().equals(admin)
                || grantedAuthority.getAuthority().equals(superuser)))
        .orElse(false);
  }

  public boolean isCurrentUserUser() {
    return isCurrentUserInRole(user);
  }

  public boolean isCurrentUserModerator() {
    return isCurrentUserInRole(moderator);
  }

  public boolean isCurrentUserAdmin() {
    return isCurrentUserInRole(admin);
  }

  public boolean isCurrentUserSuperuser() {
    return isCurrentUserInRole(superuser);
  }

  /**
   * If the current user has a specific authority (security role).
   * <p>
   * The name of this method comes from the isUserInRole() method in the Servlet API
   *
   * @param authority the authority to check
   * @return true if the current user has the authority, false otherwise
   */
  private static boolean isCurrentUserInRole(String authority) {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(securityContext.getAuthentication())
        .map(authentication -> authentication.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority)))
        .orElse(false);
  }
}
