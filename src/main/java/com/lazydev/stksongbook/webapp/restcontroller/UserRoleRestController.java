package com.lazydev.stksongbook.webapp.restcontroller;

import com.lazydev.stksongbook.webapp.service.UserRoleService;
import com.lazydev.stksongbook.webapp.model.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user_roles")
public class UserRoleRestController {

    @Autowired
    private UserRoleService manager;

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
    //@ResponseStatus(HttpStatus.CREATED)
    public UserRole addUserRole(@RequestBody UserRole obj) {
        return manager.save(obj);
    }

    @PutMapping   // Add mapping?
    //@ResponseStatus(HttpStatus.OK)
    public UserRole updateUserRole(@RequestBody UserRole obj) {
        return manager.save(obj);
    }

    @DeleteMapping   // Add mapping?
    public void deleteUserRole(@RequestParam Long id) {
        manager.deleteById(id);
    }
}
