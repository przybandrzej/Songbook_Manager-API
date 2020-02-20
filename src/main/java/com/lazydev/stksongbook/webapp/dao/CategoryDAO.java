package com.lazydev.stksongbook.webapp.dao;

import com.lazydev.stksongbook.webapp.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDAO extends CrudRepository<Category, Long> {
}
