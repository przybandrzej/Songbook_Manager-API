package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Category;
import com.lazydev.stksongbook.webapp.repository.CategoryRepository;
import com.lazydev.stksongbook.webapp.service.exception.CannotDeleteEntityException;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

  private CategoryRepository repository;

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

  public Category save(Category saveAuthor) {
    return repository.save(saveAuthor);
  }

  public void deleteById(Long id) {
    var category = findById(id);
    if(!category.getSongs().isEmpty()) {
      throw new CannotDeleteEntityException(Category.class.getSimpleName(), "There are songs belonging to this category.");
    }
    repository.deleteById(id);
  }
}
