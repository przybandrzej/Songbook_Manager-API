package com.lazydev.stksongbook.webapp.data.repository;

import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSongRepository extends JpaRepository<UserSongRating, Long> {
}
