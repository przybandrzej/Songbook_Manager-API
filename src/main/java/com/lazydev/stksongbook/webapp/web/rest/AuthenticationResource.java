package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.security.UserContextService;
import com.lazydev.stksongbook.webapp.security.jwt.JWTConfigurer;
import com.lazydev.stksongbook.webapp.security.jwt.TokenProvider;
import com.lazydev.stksongbook.webapp.service.MailerService;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.*;
import com.lazydev.stksongbook.webapp.service.dto.change.EmailChangeDTO;
import com.lazydev.stksongbook.webapp.service.dto.change.NameChangeDTO;
import com.lazydev.stksongbook.webapp.service.dto.change.PasswordChangeDTO;
import com.lazydev.stksongbook.webapp.service.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthenticationResource {

  private final Logger log = LoggerFactory.getLogger(AuthenticationResource.class);

  private final UserService service;
  private final AuthenticationManager authenticationManager;
  private final TokenProvider tokenProvider;
  private final MailerService mailerService;
  private final UserContextService userContextService;
  private final UserMapper userMapper;

  @PostMapping("/register")
  public ResponseEntity<Void> register(@RequestBody @Valid RegisterNewUserForm form) {
    log.debug("Request to register user {}", form.getUsername());
    User user = service.register(form);
    mailerService.sendActivationEmail(user);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping("/authenticate")
  public ResponseEntity<TokenDTO> authenticate(@Valid @RequestBody LoginForm form) {
    log.debug("Request to login user {}", form.getLogin());
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(form.getLogin(), form.getPassword());
    Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = tokenProvider.createToken(authentication, form.isRememberMe());
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
    return new ResponseEntity<>(new TokenDTO(jwt), httpHeaders, HttpStatus.OK);
  }

  @GetMapping("/activate")
  public ResponseEntity<Void> activateAccount(@RequestParam(value = "key") String key) {
    log.debug("Request to activate account by key {}", key);
    service.activate(key);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/is-authenticated")
  public ResponseEntity<Boolean> isAuthenticated() {
    log.debug("Request to check if authenticated");
    return ResponseEntity.ok(userContextService.isAuthenticated());
  }

  /**
   * GET  /account : get the current user.
   *
   * @return the current user
   * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
   */
  @GetMapping("/account")
  public ResponseEntity<UserDTO> getAccount() {
    log.debug("Request to get logged-in account");
    return ResponseEntity.ok(userMapper.map(userContextService.getCurrentUser()));
  }

  /**
   * POST  /account/change-password : changes the current user's password
   *
   * @param passwordChangeDto current and new password
   */
  @PostMapping("/account/change-password")
  public ResponseEntity<Void> changePassword(@RequestBody @Valid PasswordChangeDTO passwordChangeDto) {
    log.debug("Request to change password");
    service.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
    return ResponseEntity.noContent().build();
  }

  /**
   * POST   /account/reset-password/init : Send an email to reset the password of the user
   *
   * @param mail the mail of the user
   */
  @PostMapping("/account/reset-password/init")
  public ResponseEntity<Void> requestPasswordReset(@RequestBody String mail) {
    log.debug("Request for password reset for {}", mail);
    mailerService.sendPasswordResetMail(service.requestPasswordReset(mail));
    return ResponseEntity.noContent().build();
  }

  /**
   * POST   /account/reset-password/finish : Finish to reset the password of the user
   *
   * @param keyAndPassword the generated key and the new password
   * @throws RuntimeException 500 (Internal Server Error) if the password could not be reset
   */
  @PostMapping("/account/reset-password/finish")
  public ResponseEntity<Void> finishPasswordReset(@RequestBody TokenAndPasswordDTO keyAndPassword) {
    log.debug("Request to finish password reset");
    service.completePasswordReset(keyAndPassword.getToken(), keyAndPassword.getNewPassword());
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/account/change-email")
  public ResponseEntity<Void> changeEmail(@RequestBody @Valid EmailChangeDTO emailChangeDTO) {
    log.debug("Request to change email");
    User user = service.changeEmail(emailChangeDTO);
    mailerService.sendUserEmailChangedEmail(user);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/account/change-first-name")
  public ResponseEntity<Void> changeFirstName(@RequestBody @Valid NameChangeDTO nameChangeDTO) {
    log.debug("Request to change first name");
    service.changeFirstName(nameChangeDTO);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/account/change-last-name")
  public ResponseEntity<Void> changeLastName(@RequestBody @Valid NameChangeDTO nameChangeDTO) {
    log.debug("Request to change last name");
    service.changeLastName(nameChangeDTO);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/account/change-image/{url}")
  public ResponseEntity<Void> changeImageUrl(@PathVariable String url) {
    log.debug("Request to change image url");
    service.changeImage(url);
    return ResponseEntity.noContent().build();
  }
}
