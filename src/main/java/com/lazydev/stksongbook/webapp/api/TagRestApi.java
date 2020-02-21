package com.lazydev.stksongbook.webapp.api;

import com.lazydev.stksongbook.webapp.manager.TagManager;
import com.lazydev.stksongbook.webapp.model.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tags")
public class TagRestApi {

    @Autowired
    private TagManager manager;

    @GetMapping("/get")
    public Iterable<Tag> getAll(){
        return manager.findAll();
    }

    @GetMapping("/get/id/{id}")
    public Optional<Tag> getById(@PathVariable("id") Long id) {
        return manager.findById(id);
    }

    @GetMapping("/get/name/{name}")
    public Iterable<Tag> getByName(@PathVariable("name") String name){
        return manager.findByName(name);
    }

    @PostMapping   // Add mapping?
    public Tag addTag(@RequestBody Tag obj) {
        return manager.save(obj);
    }

    @PutMapping   // Add mapping?
    public Tag updateTag(@RequestBody Tag obj) {
        return manager.save(obj);
    }

    @DeleteMapping   // Add mapping?
    public void deleteAuthor(@RequestParam Long id) {
        manager.deleteById(id);
    }
}
