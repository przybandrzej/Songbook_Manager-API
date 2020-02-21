package com.lazydev.stksongbook.webapp.api;

import com.lazydev.stksongbook.webapp.manager.UserRoleManager;
import com.lazydev.stksongbook.webapp.model.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user_roles")
public class UserRoleRestApi {

    @Autowired
    private UserRoleManager manager;

    @GetMapping("/get")
    public Iterable<UserRole> getAll(){
        return manager.findAll();
    }

    @GetMapping("/get/id/{id}")
    public Optional<UserRole> getById(@PathVariable("id") Long id) {
        return manager.findById(id);
    }

    @GetMapping("/get/name/{name}")
    public Iterable<UserRole> getByName(@PathVariable("name") String name){
        return manager.findByName(name);
    }

    @PostMapping   // Add mapping?
    public UserRole addUserRole(@RequestBody UserRole obj) {
        return manager.save(obj);
    }

    @PutMapping   // Add mapping?
    public UserRole updateUserRole(@RequestBody UserRole obj) {
        return manager.save(obj);
    }

    @DeleteMapping   // Add mapping?
    public void deleteUserRole(@RequestParam Long id) {
        manager.deleteById(id);
    }
}
