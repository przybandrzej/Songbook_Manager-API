package com.lazydev.stksongbook.webapp.security;

import com.lazydev.stksongbook.webapp.data.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

  @Value("${spring.flyway.placeholders.role.superuser}")
  private static String superuser;

  @Value("${spring.flyway.placeholders.role.admin}")
  private static String admin;

  @Value("${spring.flyway.placeholders.role.moderator}")
  private static String moderator;

  @Value("${spring.flyway.placeholders.role.user}")
  private static String user;

  private SecurityUtils() {
  }

  /**
   * Get the login of the current user.
   *
   * @return the login of the current user
   */
  public static Optional<String> getCurrentUserLogin() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(securityContext.getAuthentication())
        .map(authentication -> {
          if(authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
          } else if(authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
          }
          return null;
        });
  }

  /**
   * Get the JWT of the current user.
   *
   * @return the JWT of the current user
   */
  public static Optional<String> getCurrentUserJWT() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(securityContext.getAuthentication())
        .filter(authentication -> authentication.getCredentials() instanceof String)
        .map(authentication -> (String) authentication.getCredentials());
  }

  /**
   * Check if a user is authenticated.
   *
   * @return true if the user is authenticated, false otherwise
   */
  public static boolean isAuthenticated() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(securityContext.getAuthentication())
        .map(authentication -> authentication.getAuthorities().stream()
            .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(user)
                || grantedAuthority.getAuthority().equals(moderator)
                || grantedAuthority.getAuthority().equals(admin)
                || grantedAuthority.getAuthority().equals(superuser)))
        .orElse(false);
  }

  public static boolean isCurrentUserUser() {
    return isCurrentUserInRole(user);
  }

  public static boolean isCurrentUserModerator() {
    return isCurrentUserInRole(moderator);
  }

  public static boolean isCurrentUserAdmin() {
    return isCurrentUserInRole(admin);
  }

  public static boolean isCurrentUserSuperuser() {
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

  /**
   * @param claimName claim to get
   * @return value, if user has a claim, null otherwise
   */
  public static String getClaim(String claimName) {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(securityContext.getAuthentication())
        .flatMap(authentication -> authentication.getAuthorities().stream()
            .filter(authority -> authority instanceof KeyValueGrantedAuthority
                && claimName.equals(((KeyValueGrantedAuthority) authority).getKey())).
                map(GrantedAuthority::getAuthority).findFirst())
        .orElse(null);
  }
}
