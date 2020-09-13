package com.lazydev.stksongbook.webapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

  private Authentication authentication = new Authentication();
  private CorsConfiguration cors = new CorsConfiguration();

  public CorsConfiguration getCors() {
    return cors;
  }

  public void setCors(CorsConfiguration cors) {
    this.cors = cors;
  }

  public Authentication getAuthentication() {
    return authentication;
  }

  public void setAuthentication(Authentication authentication) {
    this.authentication = authentication;
  }

  public static class Authentication {

    private Jwt jwt = new Jwt();

    public Jwt getJwt() {
      return jwt;
    }

    public void setJwt(Jwt jwt) {
      this.jwt = jwt;
    }

    public static class Jwt {
      private String secret;
      private long tokenValidityInSeconds;
      private long tokenValidityInSecondsForRememberMe;

      public long getTokenValidityInSecondsForRememberMe() {
        return tokenValidityInSecondsForRememberMe;
      }

      public void setTokenValidityInSecondsForRememberMe(long tokenValidityInSecondsForRememberMe) {
        this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
      }

      public String getSecret() {
        return secret;
      }

      public void setSecret(String secret) {
        this.secret = secret;
      }

      public long getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
      }

      public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
        this.tokenValidityInSeconds = tokenValidityInSeconds;
      }
    }
  }
}
