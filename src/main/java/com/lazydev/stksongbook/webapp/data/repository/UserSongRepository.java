package com.lazydev.stksongbook.webapp.data.repository;

import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import org.springframework.data.repository.CrudRepository;

public interface UserSongRepository extends CrudRepository<UserSongRating, Long> {
}
