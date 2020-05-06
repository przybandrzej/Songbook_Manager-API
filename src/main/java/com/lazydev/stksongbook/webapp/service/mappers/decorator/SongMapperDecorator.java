package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.data.model.*;
import com.lazydev.stksongbook.webapp.service.*;
import com.lazydev.stksongbook.webapp.service.dto.SongDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateCoauthorDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateSongDTO;
import com.lazydev.stksongbook.webapp.service.mappers.SongMapper;
import com.lazydev.stksongbook.webapp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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
    song.setUsersSongs(new HashSet<>(userService.findBySong(dto.getId())));
    song.setRatings(new HashSet<>(userSongRatingService.findBySongId(dto.getId())));
    song.setPlaylists(new HashSet<>(playlistService.findBySongId(dto.getId(), true)));
    return song;
  }

  @Override
  public Song map(CreateSongDTO dto) {
    Song song = delegate.map(dto);
    song.setId(Constants.DEFAULT_ID);
    song.setTags(new HashSet<>());
    song.setCategory(categoryService.findById(dto.getCategoryId()));
    song.setUsersSongs(new HashSet<>());
    song.setRatings(new HashSet<>());
    song.setPlaylists(new HashSet<>());
    song.setCreationTime(LocalDateTime.now());
    song.setCoauthors(new HashSet<>());
    return song;
  }

  /*private SongCoauthor createCoauthor(CreateCoauthorDTO dto, Song song) {
    Author author = authorService.findByName(dto.getAuthorName());
    SongCoauthor coauthor = new SongCoauthor();
    coauthor.setFunction(dto.getFunction());
    coauthor.setSong(song);
    author.addCoauthorSong(coauthor);
    coauthor.setAuthor(author);
    coauthor.setId(new SongsCoauthorsKey(song.getId(), author.getId()));
    return coauthor;
  }*/
}
