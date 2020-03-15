package com.lazydev.stksongbook.webapp.data.service;

import com.lazydev.stksongbook.webapp.data.repository.UserRepository;
import com.lazydev.stksongbook.webapp.data.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository dao;

    public Optional<User> findById(Long id) {
        return dao.findById(id);
    }

    public Iterable<User> findByUsername(String name) {
        List<User> list = new ArrayList<>();
        for (User element : dao.findAll()) {
            if(element.getUsername().equals(name)) list.add(element);
        }
        return list;
    }

    public Iterable<User> findAll() {
        return dao.findAll();
    }

    public User save(User saveUser) {
        return dao.save(saveUser);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}
