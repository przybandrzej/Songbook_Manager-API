package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.repository.UserRoleRepository;
import com.lazydev.stksongbook.webapp.security.UserContextService;
import com.lazydev.stksongbook.webapp.service.dto.UserRoleDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.exception.CannotDeleteEntityException;
import com.lazydev.stksongbook.webapp.service.exception.EntityAlreadyExistsException;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.exception.ForbiddenOperationException;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class UserRoleService {

  @Value("${spring.flyway.placeholders.role.superuser}")
  private String superuserRoleName;
  @Value("${spring.flyway.placeholders.role.admin}")
  private String adminRoleName;

  private final UserRoleRepository repository;
  private final UserContextService userContextService;

  public UserRoleService(UserRoleRepository repository, UserContextService userContextService) {
    this.repository = repository;
    this.userContextService = userContextService;
  }

  public Optional<UserRole> findByIdNoException(Long id) {
    return repository.findById(id);
  }

  public UserRole findById(Long id) {
    return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(UserRole.class, id));
  }

  public List<UserRole> findByNameFragment(String value) {
    return repository.findByNameContainingIgnoreCase(value);
  }

  public UserRole findByName(String name) {
    return repository.findByName(name)
        .orElseThrow(() -> new EntityNotFoundException(UserRole.class, name));
  }

  public Optional<UserRole> findByNameNoException(String name) {
    return repository.findByName(name);
  }

  public List<UserRole> findAll() {
    return repository.findAll();
  }

  public UserRole create(@Valid UniversalCreateDTO saveRole) {
    User user = userContextService.getCurrentUser();
    if(!(user.getUserRole().getName().equals(superuserRoleName)
        || user.getUserRole().getName().equals(adminRoleName))) {
      throw new ForbiddenOperationException("Roles can be crated only by administrators.");
    }
    if(findByNameNoException(saveRole.getName()).isPresent()) {
      throw new EntityAlreadyExistsException(UserRole.class.getSimpleName(), saveRole.getName());
    }
    UserRole role = new UserRole();
    role.setId(Constants.DEFAULT_ID);
    role.setName(saveRole.getName());
    return repository.save(role);
  }

  public UserRole update(@Valid UserRoleDTO saveRole) {
    User user = userContextService.getCurrentUser();
    if(!(user.getUserRole().getName().equals(superuserRoleName)
        || user.getUserRole().getName().equals(adminRoleName))) {
      throw new ForbiddenOperationException("Roles can be updated only by administrators.");
    }
    UserRole role = findById(saveRole.getId());
    role.setName(saveRole.getName());
    return repository.save(role);
  }

  public void deleteById(Long id) {
    User user = userContextService.getCurrentUser();
    if(!(user.getUserRole().getName().equals(superuserRoleName)
        || user.getUserRole().getName().equals(adminRoleName))) {
      throw new ForbiddenOperationException("Roles can be deleted only by administrators.");
    }
    var role = findById(id);
    if(!role.getUsers().isEmpty()) {
      throw new CannotDeleteEntityException(UserRole.class.getSimpleName(), "There are users belonging to this role.");
    }
    repository.deleteById(id);
  }
}
