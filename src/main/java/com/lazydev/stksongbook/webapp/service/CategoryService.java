package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Category;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.repository.CategoryRepository;
import com.lazydev.stksongbook.webapp.security.UserContextService;
import com.lazydev.stksongbook.webapp.service.dto.CategoryDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.exception.CannotDeleteEntityException;
import com.lazydev.stksongbook.webapp.service.exception.EntityAlreadyExistsException;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.exception.ForbiddenOperationException;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Validated
public class CategoryService {

  @Value("${spring.flyway.placeholders.role.superuser}")
  private String superuserRoleName;
  @Value("${spring.flyway.placeholders.role.admin}")
  private String adminRoleName;
  @Value("${spring.flyway.placeholders.role.moderator}")
  private String moderatorRoleName;

  private final CategoryRepository repository;
  private final UserContextService userContextService;

  public CategoryService(CategoryRepository repository, UserContextService userContextService) {
    this.repository = repository;
    this.userContextService = userContextService;
  }

  public Optional<Category> findByIdNoException(Long id) {
    return repository.findById(id);
  }

  public Category findById(Long id) {
    return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(Category.class, id));
  }

  public List<Category> findAll() {
    return repository.findAll();
  }

  public Optional<Category> findByNameNoException(String name) {
    return repository.findByName(name);
  }

  public Category findByName(String name) {
    return repository.findByName(name).orElseThrow(() -> new EntityNotFoundException(Category.class, name));
  }

  public List<Category> findByNameFragment(String name) {
    return repository.findByNameContainingIgnoreCase(name);
  }

  public void deleteById(Long id) {
    User user = userContextService.getCurrentUser();
    if(!(user.getUserRole().getName().equals(superuserRoleName)
        || user.getUserRole().getName().equals(adminRoleName)
        || user.getUserRole().getName().equals(moderatorRoleName))) {
      throw new ForbiddenOperationException("Forbidden operation.");
    }
    var category = findById(id);
    if(!category.getSongs().isEmpty()) {
      throw new CannotDeleteEntityException(Category.class.getSimpleName(), "There are songs belonging to this category.");
    }
    repository.deleteById(id);
  }

  public Category create(@Valid UniversalCreateDTO categoryDto) {
    User user = userContextService.getCurrentUser();
    if(!(user.getUserRole().getName().equals(superuserRoleName)
        || user.getUserRole().getName().equals(adminRoleName)
        || user.getUserRole().getName().equals(moderatorRoleName))) {
      throw new ForbiddenOperationException("Forbidden operation.");
    }
    var optional = findByNameNoException(categoryDto.getName());
    if(optional.isPresent()) {
      throw new EntityAlreadyExistsException(Category.class.getSimpleName(), optional.get().getId(), optional.get().getName());
    }
    Category category = new Category();
    category.setId(Constants.DEFAULT_ID);
    category.setName(categoryDto.getName());
    return repository.save(category);
  }

  public Category update(CategoryDTO categoryDto) {
    User user = userContextService.getCurrentUser();
    if(!(user.getUserRole().getName().equals(superuserRoleName)
        || user.getUserRole().getName().equals(adminRoleName)
        || user.getUserRole().getName().equals(moderatorRoleName))) {
      throw new ForbiddenOperationException("Forbidden operation.");
    }
    Category category = findById(categoryDto.getId());
    category.setName(categoryDto.getName());
    return repository.save(category);
  }
}
