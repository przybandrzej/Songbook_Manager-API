package com.lazydev.stksongbook.webapp.data.service;

import com.lazydev.stksongbook.webapp.data.repository.UserRoleRepository;
import com.lazydev.stksongbook.webapp.data.model.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserRoleService {

    @Autowired
    private UserRoleRepository repository;

    public Optional<UserRole> findById(Long id) {
        return repository.findById(id);
    }

    public Iterable<UserRole> findByName(String name) {
        List<UserRole> list = new ArrayList<>();
        for (UserRole element : repository.findAll()) {
            if(element.getName().equals(name)) list.add(element);
        }
        return list;
    }

    public Iterable<UserRole> findAll() {
        return repository.findAll();
    }

    public UserRole save(UserRole saveRole) {
        return repository.save(saveRole);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
