package com.lazydev.stksongbook.webapp.data.service;

import com.lazydev.stksongbook.webapp.data.model.Tag;
import com.lazydev.stksongbook.webapp.data.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TagService {

    private TagRepository repository;

    public Optional<Tag> findById(Long id) {
        return repository.findById(id);
    }

    public List<Tag> findByName(String name) {
        return repository.findByNameIgnoreCase(name);
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
