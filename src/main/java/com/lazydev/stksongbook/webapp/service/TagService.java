package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.dao.TagDAO;
import com.lazydev.stksongbook.webapp.model.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TagService {

    @Autowired
    private TagDAO dao;

    public Optional<Tag> findById(Long id) {
        return dao.findById(id);
    }

    public Iterable<Tag> findByName(String name) {
        List<Tag> list = new ArrayList<>();
        for (Tag element : dao.findAll()) {
            if(element.getName().equals(name)) list.add(element);
        }
        return list;
    }

    public Iterable<Tag> findAll() {
        return dao.findAll();
    }

    public Tag save(Tag saveObj) {
        return dao.save(saveObj);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}
