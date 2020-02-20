package com.lazydev.stksongbook.webapp.dao;

import com.lazydev.stksongbook.webapp.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDAO extends CrudRepository<Tag, Long> {
}
