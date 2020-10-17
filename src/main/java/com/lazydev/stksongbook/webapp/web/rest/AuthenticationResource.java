package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.security.UserContextService;
import com.lazydev.stksongbook.webapp.security.jwt.JWTConfigurer;
import com.lazydev.stksongbook.webapp.security.jwt.TokenProvider;
import com.lazydev.stksongbook.webapp.service.MailerService;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.*;
import com.lazydev.stksongbook.webapp.service.dto.creational.RegisterNewUserForm;
import com.lazydev.stksongbook.webapp.service.exception.EmailAlreadyUsedException;
import com.lazydev.stksongbook.webapp.service.exception.UsernameAlreadyUsedException;
import com.lazydev.stksongbook.webapp.service.mappers.UserMapper;
import com.lazydev.stksongbook.webapp.util.Constants;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
    if(service.findByEmailNoException(form.getEmail()).isPresent()) {
      throw new EmailAlreadyUsedException();
    }
    if(service.findByUsernameNoException(form.getUsername()).isPresent()) {
      throw new UsernameAlreadyUsedException(form.getUsername());
    }
    User user = service.register(form);
    mailerService.sendActivationEmail(user);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping("/authenticate")
  public ResponseEntity<TokenDTO> authenticate(@Valid @RequestBody LoginForm form) {
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
  public void activateAccount(@RequestParam(value = "key") String key) {
    service.activate(key);
  }

  @GetMapping("/is-authenticated")
  public boolean isAuthenticated() {
    return userContextService.isAuthenticated();
  }

  /**
   * GET  /account : get the current user.
   *
   * @return the current user
   * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
   */
  @GetMapping("/account")
  public ResponseEntity<UserDTO> getAccount() {
    return ResponseEntity.ok(userMapper.map(userContextService.getCurrentUser()));
  }

  /**
   * POST  /account : update the current user information.
   *
   * @param userDTO the current user information
   * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
   * @throws RuntimeException          500 (Internal Server Error) if the user login wasn't found
   */
  @PostMapping("/account")
  public ResponseEntity<UserDTO> saveAccount(@Valid @RequestBody UserDTO userDTO) {
    User user = userContextService.getCurrentUser();
    User mapped = userMapper.map(userDTO.toBuilder().id(user.getId()).build());
    User saved = service.updateUser(mapped, user);
    return ResponseEntity.ok(userMapper.map(saved));
  }

  /**
   * POST  /account/change-password : changes the current user's password
   *
   * @param passwordChangeDto current and new password
   */
  @PostMapping("/account/change-password")
  public void changePassword(@RequestBody @Valid PasswordChangeDTO passwordChangeDto) {
    log.debug("Request to change password");
    service.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
  }

  /**
   * POST   /account/reset-password/init : Send an email to reset the password of the user
   *
   * @param mail the mail of the user
   */
  @PostMapping("/account/reset-password/init")
  public void requestPasswordReset(@RequestBody String mail) {
    log.debug("Request for password reset for {}", mail);
    mailerService.sendPasswordResetMail(service.requestPasswordReset(mail));
  }

  /**
   * POST   /account/reset-password/finish : Finish to reset the password of the user
   *
   * @param keyAndPassword the generated key and the new password
   * @throws RuntimeException 500 (Internal Server Error) if the password could not be reset
   */
  @PostMapping("/account/reset-password/finish")
  public void finishPasswordReset(@RequestBody TokenAndPasswordDTO keyAndPassword) {
    log.debug("Request to finish password reset");
    service.completePasswordReset(keyAndPassword.getToken(), keyAndPassword.getNewPassword());
  }

  @PatchMapping("/account/change-email")
  public void changeEmail(@RequestBody @Valid EmailChangeDTO emailChangeDTO) {
    log.debug("Request to finish password reset");
    User user = service.changeEmail(emailChangeDTO);
    mailerService.sendUserEmailChangedEmail(user);
  }
}
