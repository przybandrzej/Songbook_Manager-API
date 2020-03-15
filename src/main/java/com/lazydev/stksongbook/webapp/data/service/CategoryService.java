package com.lazydev.stksongbook.webapp.data.service;

import com.lazydev.stksongbook.webapp.data.repository.CategoryRepository;
import com.lazydev.stksongbook.webapp.data.model.Category;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    @Autowired
    private CategoryRepository dao;

    public Optional<Category> findById(Long id) {
        return dao.findById(id);
    }

    public Iterable<Category> findByName(String name) {
        List<Category> list = new ArrayList<>();
        for (Category element : dao.findAll()) {
            if(element.getName().equals(name)) list.add(element);
        }
        return list;
    }

    public Iterable<Category> findAll() {
        return dao.findAll();
    }

    public Category save(Category saveAuthor) {
        return dao.save(saveAuthor);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}
