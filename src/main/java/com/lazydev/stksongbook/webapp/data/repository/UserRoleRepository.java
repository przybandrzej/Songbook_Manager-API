package com.lazydev.stksongbook.webapp.data.repository;

import com.lazydev.stksongbook.webapp.data.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

  List<UserRole> findByNameIgnoreCase(String name);
}
