package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.dao.AuthorDAO;
import com.lazydev.stksongbook.webapp.model.Author;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {

    @Autowired
    private AuthorDAO authorDao;

    public Optional<Author> findById(Long id) {
        return authorDao.findById(id);
    }

    public Iterable<Author> findByName(String name) {
        List<Author> list = new ArrayList<>();
        for (Author element : authorDao.findAll()) {
            if(element.getName().equals(name)) list.add(element);
        }
        return list;
    }

    public Iterable<Author> findAll() {
        return authorDao.findAll();
    }

    public Author save(Author saveAuthor) {
        return authorDao.save(saveAuthor);
    }

    public void deleteById(Long id) {
        authorDao.deleteById(id);
    }
}
