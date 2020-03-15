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
    private UserRoleRepository dao;

    public Optional<UserRole> findById(Long id) {
        return dao.findById(id);
    }

    public Iterable<UserRole> findByName(String name) {
        List<UserRole> list = new ArrayList<>();
        for (UserRole element : dao.findAll()) {
            if(element.getName().equals(name)) list.add(element);
        }
        return list;
    }

    public Iterable<UserRole> findAll() {
        return dao.findAll();
    }

    public UserRole save(UserRole saveRole) {
        return dao.save(saveRole);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}
