package com.lazydev.stksongbook.webapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.flyway")
public class FlywayProperties {


  public static class Placeholders {

    Role role = new Role();
    Superuser superuser = new Superuser();

    public Role getRole() {
      return role;
    }

    public void setRole(Role role) {
      this.role = role;
    }

    public Superuser getSuperuser() {
      return superuser;
    }

    public void setSuperuser(Superuser superuser) {
      this.superuser = superuser;
    }

    public static class Role {
      private String user;
      private String moderator;
      private String admin;
      private String superuser;

      public String getUser() {
        return user;
      }

      public void setUser(String user) {
        this.user = user;
      }

      public String getModerator() {
        return moderator;
      }

      public void setModerator(String moderator) {
        this.moderator = moderator;
      }

      public String getAdmin() {
        return admin;
      }

      public void setAdmin(String admin) {
        this.admin = admin;
      }

      public String getSuperuser() {
        return superuser;
      }

      public void setSuperuser(String superuser) {
        this.superuser = superuser;
      }
    }

    public static class Superuser {
      private String username;
      private String email;
      private String firstName;
      private String lastName;
      private String passwordHash;

      public String getUsername() {
        return username;
      }

      public void setUsername(String username) {
        this.username = username;
      }

      public String getEmail() {
        return email;
      }

      public void setEmail(String email) {
        this.email = email;
      }

      public String getFirstName() {
        return firstName;
      }

      public void setFirstName(String firstName) {
        this.firstName = firstName;
      }

      public String getLastName() {
        return lastName;
      }

      public void setLastName(String lastName) {
        this.lastName = lastName;
      }

      public String getPasswordHash() {
        return passwordHash;
      }

      public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
      }
    }
  }
}
