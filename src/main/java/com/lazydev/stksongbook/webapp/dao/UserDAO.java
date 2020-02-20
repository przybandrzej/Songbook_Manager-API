package com.lazydev.stksongbook.webapp.dao;

import com.lazydev.stksongbook.webapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends CrudRepository<User, Long> {
}
