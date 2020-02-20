package com.lazydev.stksongbook.webapp.api;

import com.lazydev.stksongbook.webapp.manager.AuthorManager;
import com.lazydev.stksongbook.webapp.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestApi {

    private AuthorManager manager;

    @Autowired
    public AuthorRestApi(AuthorManager manager){
        this.manager = manager;
    }

    @GetMapping("/get")
    public Iterable<Author> getAll(){
        return manager.findAll();
    }

    @GetMapping("/get/id")
    public Optional<Author> getById(@RequestParam Long id) {
        return manager.findById(id);
    }

    @GetMapping("/get/name")
    public Iterable<Author> getByName(@RequestParam String name){
        return manager.findByName(name);
    }

    @PostMapping
    public Author addAuthor(@RequestBody Author author) {
        return manager.save(author);
    }

    @PutMapping
    public Author updateAuthor(@RequestBody Author author) {
        return manager.save(author);
    }

    @DeleteMapping
    public void deleteAuthor(@RequestParam Long id) {
        manager.deleteById(id);
    }
}
