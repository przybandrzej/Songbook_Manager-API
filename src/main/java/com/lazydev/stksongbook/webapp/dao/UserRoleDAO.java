package com.lazydev.stksongbook.webapp.dao;

import com.lazydev.stksongbook.webapp.model.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDAO extends CrudRepository<UserRole, Long> {
}
