package com.lazydev.stksongbook.webapp.restcontroller;

import com.lazydev.stksongbook.webapp.service.CategoryService;
import com.lazydev.stksongbook.webapp.model.Category;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryRestController {

    @Autowired
    private CategoryService manager;

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
