package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.repository.CategoryRepository;
import com.lazydev.stksongbook.webapp.data.model.Category;
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

    public List<Category> findByName(String name) {
        return repository.findByNameIgnoreCase(name);
    }

    public Category save(Category saveAuthor) {
        return repository.save(saveAuthor);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
