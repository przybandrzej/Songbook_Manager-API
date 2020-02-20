package com.lazydev.stksongbook.webapp.manager;

import com.lazydev.stksongbook.webapp.dao.AuthorDAO;
import com.lazydev.stksongbook.webapp.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorManager {

    private AuthorDAO authorDao;

    @Autowired
    public AuthorManager(AuthorDAO dao) {
        this.authorDao = dao;
    }

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
