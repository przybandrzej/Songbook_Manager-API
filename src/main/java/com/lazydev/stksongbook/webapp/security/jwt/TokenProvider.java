package com.lazydev.stksongbook.webapp.security.jwt;

import com.lazydev.stksongbook.webapp.config.SecurityProperties;
import com.lazydev.stksongbook.webapp.repository.UserRepository;
import com.lazydev.stksongbook.webapp.security.KeyValueGrantedAuthority;
import com.lazydev.stksongbook.webapp.service.exception.NotAuthenticatedException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

  private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

  private static final String AUTHORITIES_KEY = "auth";

  private final Base64.Encoder encoder = Base64.getEncoder();

  private String secretKey;

  private long tokenValidityInMilliseconds;
  private long tokenValidityInMillisecondsForRememberMe;
  private final UserRepository userRepository;

  private final SecurityProperties securityProperties;

  public TokenProvider(SecurityProperties securityProperties, UserRepository userRepository) {
    this.securityProperties = securityProperties;
    this.userRepository = userRepository;
  }

  @PostConstruct
  public void init() {
    this.secretKey = encoder.encodeToString(securityProperties.getAuthentication().getJwt()
        .getSecret().getBytes(StandardCharsets.UTF_8));

    this.tokenValidityInMilliseconds =
        1000 * securityProperties.getAuthentication().getJwt().getTokenValidityInSeconds();
    this.tokenValidityInMillisecondsForRememberMe =
        1000 * securityProperties.getAuthentication().getJwt()
            .getTokenValidityInSecondsForRememberMe();
  }

  public String createToken(Authentication authentication, boolean rememberMe) {
    String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    long now = (new Date()).getTime();
    Date validity;
    if (rememberMe) {
      validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
    } else {
      validity = new Date(now + this.tokenValidityInMilliseconds);
    }

    JwtBuilder jwtBuilder = Jwts.builder()
        .setSubject(authentication.getName())
        .claim(AUTHORITIES_KEY, authorities)
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .setExpiration(validity);


    authentication.getAuthorities().stream().filter(authority -> authority instanceof KeyValueGrantedAuthority).forEach(
        authority -> this.addClaim((KeyValueGrantedAuthority) authority, jwtBuilder));

    return jwtBuilder.compact();
  }

  private void addClaim(KeyValueGrantedAuthority keyValueGrantedAuthority, JwtBuilder jwtBuilder) {
    jwtBuilder.claim(keyValueGrantedAuthority.getKey(), keyValueGrantedAuthority.getAuthority());
    log.debug("Adding keyValueAuthority : {}", keyValueGrantedAuthority);
  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody();

    Collection<GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    Collection<? extends GrantedAuthority> keyValueAuthorities = claims.entrySet().stream().filter(entry -> !AUTHORITIES_KEY.equals(entry.getKey()))
        .map(entry -> new KeyValueGrantedAuthority(entry.getKey(), entry.getValue().toString()))
        .collect(Collectors.toList());

    authorities.addAll(keyValueAuthorities);

    User principal = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  public boolean validateToken(String authToken) {
    try {
      Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
      return claimedUserExists(claimsJws.getBody().getSubject()) && hasClaimedRoles(claimsJws.getBody());
    } catch(SignatureException e) {
      log.info("Invalid JWT signature.");
      log.trace("Invalid JWT signature trace: {}", e);
    } catch(MalformedJwtException e) {
      log.info("Invalid JWT token.");
      log.trace("Invalid JWT token trace: {}", e);
    } catch(ExpiredJwtException e) {
      log.info("Expired JWT token.");
      log.trace("Expired JWT token trace: {}", e);
    } catch(UnsupportedJwtException e) {
      log.info("Unsupported JWT token.");
      log.trace("Unsupported JWT token trace: {}", e);
    } catch(IllegalArgumentException e) {
      log.info("JWT token compact of handler are invalid.");
      log.trace("JWT token compact of handler are invalid trace: {}", e);
    }
    return false;
  }

  private boolean claimedUserExists(String claimsSubject) {
    return userRepository.findByUsername(claimsSubject).or(() -> userRepository.findByEmailIgnoreCase(claimsSubject)).isPresent();
  }

  private boolean hasClaimedRoles(Claims claims) {
    String claimedRole = String.valueOf(claims.get(AUTHORITIES_KEY));
    if (claimedRole == null){
      return false;
    }
    String authName = claims.getSubject();
    String userRole = userRepository.findByUsername(authName).or(() -> userRepository.findByEmailIgnoreCase(authName))
        .orElseThrow(NotAuthenticatedException::new)
        .getUserRole().getName();
    return userRole.equals(claimedRole);
  }
}
