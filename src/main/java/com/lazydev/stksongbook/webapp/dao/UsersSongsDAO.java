package com.lazydev.stksongbook.webapp.dao;

import com.lazydev.stksongbook.webapp.model.UsersSongsRatingsEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersSongsDAO extends CrudRepository<UsersSongsRatingsEntity, Long> {
}
