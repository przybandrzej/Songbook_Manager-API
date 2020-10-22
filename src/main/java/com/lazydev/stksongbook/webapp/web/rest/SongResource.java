package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.SongAdd;
import com.lazydev.stksongbook.webapp.service.FileSystemStorageService;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.dto.*;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateCoauthorDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateSongDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateVerseDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.mappers.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/songs")
@AllArgsConstructor
public class SongResource {

  private final Logger log = LoggerFactory.getLogger(SongResource.class);

  private final SongService service;
  private final SongMapper mapper;
  private final UserSongRatingMapper userSongRatingMapper;
  private final UserMapper userMapper;
  private final PlaylistMapper playlistMapper;
  private final FileSystemStorageService storageService;
  private final SongAddMapper songAddMapper;
  private final VerseMapper verseMapper;
  private final TagMapper tagMapper;
  private final SongEditMapper editMapper;
  private final SongCoauthorMapper coauthorMapper;

  @GetMapping
  public ResponseEntity<List<SongDTO>> getAll(@RequestParam(value = "limit", required = false) Integer limit,
                                              @RequestParam(value = "include_awaiting", required = false) Boolean includeAwaiting) {
    log.debug("Request to get all songs");
    List<SongDTO> list = service.findAll(false, includeAwaiting, limit).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<SongDTO> getById(@PathVariable("id") Long id) {
    log.debug("Request to get song {}", id);
    return new ResponseEntity<>(mapper.map(service.findById(id)), HttpStatus.OK);
  }

  @GetMapping("/title/{title}")
  public ResponseEntity<List<SongDTO>> getByTitleFragment(@PathVariable("title") String title,
                                                          @RequestParam(value = "limit", required = false) Integer limit) {
    log.debug("Request to get all songs by title fragment {}", title);
    List<SongDTO> list = service.findByTitleContains(title, false, limit).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}/ratings")
  public ResponseEntity<List<UserSongRatingDTO>> getSongRatings(@PathVariable("id") Long id) {
    log.debug("Request to get song {} ratings", id);
    List<UserSongRatingDTO> list = service.findById(id).getRatings()
        .stream().map(userSongRatingMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}/users")
  public ResponseEntity<List<UserDTO>> getSongUserLibraries(@PathVariable("id") Long id) {
    List<UserDTO> list = service.findById(id).getUsersSongs()
        .stream().map(userMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}/added-by")
  public ResponseEntity<SongAddDTO> getSongAddedBy(@PathVariable("id") Long id) {
    SongAdd add = service.findById(id).getAdded();
    return new ResponseEntity<>(songAddMapper.map(add), HttpStatus.OK);
  }

  @GetMapping("/{id}/playlists")
  public ResponseEntity<List<PlaylistDTO>> getSongPlaylists(@PathVariable("id") Long id) {
    List<PlaylistDTO> list = service.findById(id).getPlaylists()
        .stream().map(playlistMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}/verses")
  public ResponseEntity<List<VerseDTO>> getSongVerses(@PathVariable("id") Long id) {
    List<VerseDTO> list = service.findById(id).getVerses()
        .stream().map(verseMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}/tags")
  public ResponseEntity<List<TagDTO>> getSongTags(@PathVariable("id") Long id) {
    List<TagDTO> list = service.findById(id).getTags()
        .stream().map(tagMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}/edits")
  public ResponseEntity<List<SongEditDTO>> getSongEdits(@PathVariable("id") Long id) {
    List<SongEditDTO> list = service.findById(id).getEdits()
        .stream().map(editMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}/coauthors")
  public ResponseEntity<List<SongCoauthorDTO>> getSongCoauthors(@PathVariable("id") Long id) {
    List<SongCoauthorDTO> list = service.findById(id).getCoauthors()
        .stream().map(coauthorMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<SongDTO> createSong(@RequestBody @Valid CreateSongDTO obj) {
    log.debug("Request to create song {} - {}", obj.getAuthorName(), obj.getTitle());
    var completeSong = service.createAndSaveSong(obj);
    return new ResponseEntity<>(mapper.map(completeSong), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<SongDTO> updateSong(@RequestBody @Valid SongDTO obj) {
    log.debug("Request to update song {}", obj.getId());
    var saved = service.updateSong(obj);
    return new ResponseEntity<>(mapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSong(@PathVariable("id") Long id) {
    log.debug("Request to delete song {}", id);
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/upload")
  public ResponseEntity<SongDTO> loadSongFromFile(@RequestParam("file") MultipartFile file) throws IOException {
    String name = storageService.store(file);
    CreateSongDTO dto = service.readSongFromFile(name);
    Song song = service.createAndSaveSong(dto);
    return new ResponseEntity<>(mapper.map(song), HttpStatus.OK);
  }

  @PatchMapping("/{id}/approve")
  public ResponseEntity<SongDTO> approveSong(@PathVariable Long id) {
    log.debug("Request to approve song {}", id);
    Optional<Song> optional = service.findByIdNoException(id);
    if(optional.isEmpty()) {
      throw new EntityNotFoundException(Song.class, id);
    }
    return new ResponseEntity<>(mapper.map(service.approveSong(optional.get())), HttpStatus.OK);
  }

  @PatchMapping("/{id}/add-tag")
  public ResponseEntity<SongDTO> addTagToSong(@PathVariable Long id, @RequestBody @Valid UniversalCreateDTO tag) {
    log.debug("Request to add tag {} to song {}", tag.getName(), id);
    var song = service.addTag(id, tag);
    return ResponseEntity.ok(mapper.map(song));
  }

  @PatchMapping("/{id}/add-tag-bulk")
  public ResponseEntity<SongDTO> addTagsToSongBulk(@PathVariable Long id, @RequestBody @Valid UniversalCreateDTO[] tags) {
    log.debug("Request to add tag {} to song {}", Arrays.stream(tags).map(UniversalCreateDTO::getName).collect(Collectors.joining(",")), id);
    Song song = service.addTags(id, tags);
    return ResponseEntity.ok(mapper.map(song));
  }

  @PatchMapping("/{id}/remove-tag/{tagId}")
  public ResponseEntity<SongDTO> removeTagFromSong(@PathVariable Long id, @PathVariable Long tagId) {
    log.debug("Request to remove tag {} from song {}", tagId, id);
    var song = service.removeTag(id, tagId);
    return ResponseEntity.ok(mapper.map(song));
  }

  @PatchMapping("/{id}/remove-tag-bulk/{tagIds}")
  public ResponseEntity<SongDTO> removeTagsFromSongBulk(@PathVariable Long id, @PathVariable Long[] tagIds) {
    log.debug("Request to remove tags {} from song {}", tagIds, id);
    var song = service.removeTags(id, tagIds);
    return ResponseEntity.ok(mapper.map(song));
  }

  @PatchMapping("/{id}/add-verse")
  public ResponseEntity<Void> addVerseToSong(@PathVariable Long id, @RequestBody @Valid CreateVerseDTO verse) {
    log.debug("Request to add verse {} to song {}", verse, id);
    service.addVerse(id, verse);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/remove-verse/{verseId}")
  public ResponseEntity<Void> removeVerseFromSong(@PathVariable Long id, @PathVariable Long verseId) {
    log.debug("Request to remove verse {} from song {}", verseId, id);
    service.removeVerse(id, verseId);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/add-coauthor")
  public ResponseEntity<Void> addCoauthorToSong(@PathVariable Long id, @RequestBody @Valid CreateCoauthorDTO coauthor) {
    log.debug("Request to add coauthor {} to song {}", coauthor, id);
    service.addCoauthor(id, coauthor);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/remove-coauthor/{coauthorId}")
  public ResponseEntity<Void> removeCoauthorFromSong(@PathVariable Long id, @PathVariable Long coauthorId) {
    log.debug("Request to remove coauthor {} from song {}", coauthorId, id);
    service.removeCoauthor(id, coauthorId);
    return ResponseEntity.noContent().build();
  }
}
