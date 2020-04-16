package com.lazydev.stksongbook.webapp.data.repository;

import com.lazydev.stksongbook.webapp.data.service.SongService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SongRepositoryTest {

  @Autowired
  private SongService service;

  @Test
  void findByAuthorId() {
    var song = service.findByAuthorId(1L);
    assertNotNull(song);
  }

  @Test
  void findByCategoryId() {
    var song = service.findByCategoryId(1L);
    assertNotNull(song);
  }

  /*@Test
  void findByTagId() {
    var song = service.findByTagId(1L);
    assertNotNull(song);
  }*/
}