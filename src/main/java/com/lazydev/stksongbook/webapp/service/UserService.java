package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.repository.UserRepository;
import com.lazydev.stksongbook.webapp.repository.UserRoleRepository;
import com.lazydev.stksongbook.webapp.service.dto.creational.RegisterNewUserForm;
import com.lazydev.stksongbook.webapp.service.exception.EntityDependentNotInitialized;
import com.lazydev.stksongbook.webapp.service.exception.SuperUserAlreadyExistsException;
import com.lazydev.stksongbook.webapp.service.exception.UserNotExistsException;
import com.lazydev.stksongbook.webapp.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  private final UserRepository repository;
  private final PlaylistService playlistService;
  private final PasswordEncoder passwordEncoder;
  private final UserRoleRepository roleRepository;
  @Value("${spring.flyway.placeholders.role.user}")
  private String userRoleName;
  @Value("${spring.flyway.placeholders.role.superuser}")
  private String superuserRoleName;

  public UserService(UserRepository repository, PlaylistService playlistService, PasswordEncoder passwordEncoder, UserRoleRepository roleRepository) {
    this.repository = repository;
    this.playlistService = playlistService;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
  }

  public Optional<User> findByIdNoException(Long id) {
    return repository.findById(id);
  }

  public User findById(Long id) {
    return repository.findById(id).orElseThrow(() -> new UserNotExistsException(id));
  }

  public Optional<User> findByUsernameNoException(String name) {
    return repository.findByUsername(name);
  }

  public User findByUsername(String name) {
    return repository.findByUsername(name).orElseThrow(() -> new UserNotExistsException(name));
  }

  public Optional<User> findByEmailNoException(String email) {
    return repository.findByEmail(email);
  }

  public User findByEmail(String email) {
    return repository.findByEmail(email).orElseThrow(() -> new UserNotExistsException(email));
  }

  public List<User> findByUsernameContains(String text) {
    return repository.findByUsernameContainingIgnoreCase(text);
  }

  public List<User> findByUserRole(Long id) {
    return repository.findByUserRoleId(id);
  }

  public List<User> findBySong(Long id) {
    return repository.findBySongsId(id);
  }

  public List<User> findAll() {
    return repository.findAll();
  }

  public List<User> findLimited(int limit) {
    return repository.findAll(PageRequest.of(0, limit)).toList();
  }

  public User save(User saveUser) {
    if(saveUser.getUserRole().getName().equals(superuserRoleName)) {
      boolean superuserExists = !roleRepository.findByName(superuserRoleName).map(role -> role.getUsers().isEmpty())
          .orElseThrow(() -> new EntityDependentNotInitialized(superuserRoleName));
      if(superuserExists) {
        throw new SuperUserAlreadyExistsException();
      }
    }
    return repository.save(saveUser);
  }

  public void deleteById(Long id) {
    var user = findById(id);
    user.getPlaylists().forEach(it -> playlistService.deleteById(it.getId()));
    repository.deleteById(id);
  }

  public User register(RegisterNewUserForm form) {
    User user = new User();
    user.setRegistrationDate(Instant.now());
    // todo user.setActivationKey();
    user.setId(Constants.DEFAULT_ID);
    user.setUsername(form.getUsername());
    user.setEmail(form.getEmail());
    // todo user.setActivated(false);
    user.setActivated(true);
    user.setFirstName(form.getFirstName());
    user.setLastName(form.getLastName());
    user.setPassword(passwordEncoder.encode(form.getPassword()));
    user.setUserRole(roleRepository.findByName(userRoleName).orElseThrow(() -> new EntityDependentNotInitialized(userRoleName)));
    return repository.save(user);
  }
}
