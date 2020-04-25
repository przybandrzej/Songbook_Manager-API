package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.service.*;
import com.lazydev.stksongbook.webapp.service.dto.SongDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateSongDTO;
import com.lazydev.stksongbook.webapp.service.mappers.SongMapper;
import com.lazydev.stksongbook.webapp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.stream.Collectors;

public abstract class SongMapperDecorator implements SongMapper {

  @Autowired
  @Qualifier("delegate")
  private SongMapper delegate;
  @Autowired
  private TagService tagService;
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private AuthorService authorService;
  @Autowired
  private UserService userService;
  @Autowired
  private UserSongRatingService userSongRatingService;
  @Autowired
  private PlaylistService playlistService;

  @Override
  public Song map(SongDTO dto) {
    Song song = delegate.map(dto);
    song.setTags(dto.getTags().stream().map(t -> tagService.findById(t)).collect(Collectors.toSet()));
    song.setCategory(categoryService.findById(dto.getCategoryId()));
    song.setAuthor(authorService.findById(dto.getAuthorId()));
    song.setUsersSongs(new HashSet<>(userService.findBySong(dto.getId())));
    song.setRatings(new HashSet<>(userSongRatingService.findBySongId(dto.getId())));
    song.setPlaylists(new HashSet<>(playlistService.findBySongId(dto.getId())));
    return song;
  }

  @Override
  public Song map(CreateSongDTO dto) {
    Song song = delegate.map(dto);
    song.setId(Constants.DEFAULT_ID);
    song.setTags(dto.getTags().stream().map(t -> tagService.findById(t)).collect(Collectors.toSet()));
    song.setCategory(categoryService.findById(dto.getCategoryId()));
    song.setAuthor(authorService.findById(dto.getAuthorId()));
    song.setUsersSongs(new HashSet<>());
    song.setRatings(new HashSet<>());
    song.setPlaylists(new HashSet<>());
    return song;
  }
}
