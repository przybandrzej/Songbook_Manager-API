package com.lazydev.stksongbook.webapp.api.restcontroller;

import com.lazydev.stksongbook.webapp.api.dto.AuthorDTO;
import com.lazydev.stksongbook.webapp.api.dto.SongAuthorDTO;
import com.lazydev.stksongbook.webapp.api.mappers.AuthorMapper;
import com.lazydev.stksongbook.webapp.api.mappers.SongAuthorMapper;
import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.data.model.SongAuthor;
import com.lazydev.stksongbook.webapp.data.service.AuthorService;
import com.lazydev.stksongbook.webapp.data.service.SongAuthorService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorRestController {

  private AuthorService service;
  private AuthorMapper authorMapper;
  private SongAuthorMapper songAuthorMapper;
  private SongAuthorService songAuthorService;
  private ApplicationEventPublisher eventPublisher;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<AuthorDTO> getAll(HttpServletResponse response) {
    //eventPublisher.publishEvent(new SingleResourceRetrieved(this, response));
    return service.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @GetMapping("/id/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public AuthorDTO getById(@PathVariable("id") Long id, HttpServletResponse response) {
    Optional<Author> optAuthor = service.findById(id);
    return optAuthor.map(this::convertToDto).orElse(null);
  }

  @GetMapping("/id/{id}/songs")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<SongAuthorDTO> getSongsAuthor(@PathVariable("id") Long id, HttpServletResponse response) {
    List<SongAuthor> authors = songAuthorService.findByAuthorId(id);
    return authors.stream().map(songAuthorMapper::songsAuthorsEntityToSongAuthorDTO).collect(Collectors.toList());
  }

  @GetMapping("/song/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<SongAuthorDTO> getBySong(@PathVariable("id") Long id, HttpServletResponse response) {
    List<SongAuthor> authors = songAuthorService.findBySongId(id);
    return authors.stream().map(songAuthorMapper::songsAuthorsEntityToSongAuthorDTO).collect(Collectors.toList());
  }

  @GetMapping("/function/{function}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<SongAuthorDTO> getSongsAuthor(@PathVariable("function") String function, HttpServletResponse response) {
    List<SongAuthor> authors = songAuthorService.findByFunction(function);
    return authors.stream().map(songAuthorMapper::songsAuthorsEntityToSongAuthorDTO).collect(Collectors.toList());
  }

  @GetMapping("/name/{name}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<AuthorDTO> getByName(@PathVariable("name") String name, HttpServletResponse response) {
    return service.findByName(name).stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public AuthorDTO create(@RequestBody AuthorDTO authorDto, HttpServletResponse response) {
    //Preconditions.checkNotNull(authorDto);
    Author author = convertToEntity(authorDto);
    Author created = service.save(author);

    //eventPublisher.publishEvent(new ResourceCreated(this, response, created.getId()));
    return convertToDto(created);
  }

  @PostMapping("id/{id}/songs")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public SongAuthorDTO create(@PathVariable("id") Long id, @RequestBody SongAuthorDTO songAuthorDTO, HttpServletResponse response) {
    //Preconditions.checkNotNull(authorDto);
    SongAuthor entity = songAuthorMapper.songsAuthorsEntityDTOToSongAuthor(songAuthorDTO);
    SongAuthor created = songAuthorService.save(entity);

    //eventPublisher.publishEvent(new ResourceCreated(this, response, created.getId()));
    return songAuthorMapper.songsAuthorsEntityToSongAuthorDTO(created);
  }

  @PutMapping("/id/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void update(@PathVariable("id") Long id, @RequestBody AuthorDTO authorDto) {
    Author author = convertToEntity(authorDto);
    author.setId(id);
    service.save(author);
  }

  @PutMapping("/id/{id}/songs")
  @ResponseStatus(HttpStatus.OK)
  public void updateSong(@PathVariable("id") Long id, @RequestBody SongAuthorDTO songAuthorDTO) {
    SongAuthor author = songAuthorMapper.songsAuthorsEntityDTOToSongAuthor(songAuthorDTO);
    songAuthorService.save(author);
  }

  @DeleteMapping("/id/{id}")
  public void delete(@PathVariable("id") Long id) {
    service.deleteById(id);
  }

    @DeleteMapping("/id/{id}/songs")
    public void deleteSong(@PathVariable("id") Long id, SongAuthorDTO songAuthorDTO) {
      var entity = songAuthorMapper.songsAuthorsEntityDTOToSongAuthor(songAuthorDTO);
      songAuthorService.delete(entity);
    }

  public AuthorDTO convertToDto(Author author) {
    return authorMapper.authorToAuthorDTO(author);
  }

  public Author convertToEntity(AuthorDTO authorDto) {
    return authorMapper.authorDTOToAuthor(authorDto);
  }
}
