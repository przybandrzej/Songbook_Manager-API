package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Tag;
import com.lazydev.stksongbook.webapp.repository.TagRepository;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TagService {

    private TagRepository repository;

    public Optional<Tag> findByIdNoException(Long id) {
        return repository.findById(id);
    }

    public Tag findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(Tag.class, id));
    }

    public List<Tag> findByNameFragment(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    public Optional<Tag> findByNameNoException(String name) {
        return repository.findByName(name);
    }

    public Tag findByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new EntityNotFoundException(Tag.class, name));
    }

    public List<Tag> findAll() {
        return repository.findAll();
    }

    public Tag save(Tag saveObj) {
        return repository.save(saveObj);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
