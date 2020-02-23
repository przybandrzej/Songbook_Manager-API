package com.lazydev.stksongbook.webapp.api;

import com.lazydev.stksongbook.webapp.manager.AuthorManager;
import com.lazydev.stksongbook.webapp.manager.CategoryManager;
import com.lazydev.stksongbook.webapp.model.Author;
import com.lazydev.stksongbook.webapp.model.Category;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryRestApi {

    @Autowired
    private CategoryManager manager;

    @GetMapping("/get")
    public Iterable<Category> getAll(){
        return manager.findAll();
    }

    @GetMapping("/get/id/{id}")
    public Optional<Category> getById(@PathVariable("id") Long id) {
        return manager.findById(id);
    }

    @GetMapping("/get/name/{name}")
    public Iterable<Category> getByName(@PathVariable("name") String name){
        return manager.findByName(name);
    }

    @PostMapping   // Add mapping?
    //@ResponseStatus(HttpStatus.CREATED)
    public Category addCategory(@RequestBody Category category) {
        return manager.save(category);
    }

    @PutMapping   // Add mapping?
    //@ResponseStatus(HttpStatus.OK)
    public Category updateCategory(@RequestBody Category category) {
        return manager.save(category);
    }

    @DeleteMapping   // Add mapping?
    public void deleteAuthor(@RequestParam Long id) {
        manager.deleteById(id);
    }

}
