package com.lazydev.stksongbook.webapp.restcontroller;

import com.lazydev.stksongbook.webapp.dto.AuthorDTO;
import com.lazydev.stksongbook.webapp.dto.AuthorMapper;
import com.lazydev.stksongbook.webapp.restcontroller.events.ResourceCreated;
import com.lazydev.stksongbook.webapp.restcontroller.events.SingleResourceRetrieved;
import com.lazydev.stksongbook.webapp.service.AuthorService;
import com.lazydev.stksongbook.webapp.model.Author;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorRestController {

    @Autowired
    private AuthorService service;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping
    public List<AuthorDTO> getAll(HttpServletResponse response){
        List<Author> list = (List<Author>) service.findAll();
        //eventPublisher.publishEvent(new SingleResourceRetrieved(this, response));
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public AuthorDTO getById(@PathVariable("id") Long id, HttpServletResponse response) {
        Optional<Author> optAuthor = service.findById(id);

        eventPublisher.publishEvent(new SingleResourceRetrieved(this, response));
        return optAuthor.map(this::convertToDto).orElse(null);
    }

    /*@GetMapping("/id/{id}/songs")
    @ResponseBody
    public AuthorDTO getAuthorSongs(@PathVariable("id") Long id, HttpServletResponse response) {
        Optional<Author> optAuthor = service.findById(id);

        eventPublisher.publishEvent(new SingleResourceRetrieved(this, response));
        return optAuthor.map(this::convertToDto).orElse(null);
    }*/

    @GetMapping("/name/{name}")
    @ResponseBody
    public List<AuthorDTO> getByName(@PathVariable("name") String name, HttpServletResponse response) {
        List<Author> list = (List<Author>) service.findByName(name);

        eventPublisher.publishEvent(new SingleResourceRetrieved(this, response));
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO create(@RequestBody AuthorDTO authorDto, HttpServletResponse response) {
        //Preconditions.checkNotNull(authorDto);
        Author author = convertToEntity(authorDto);
        Author created = service.save(author);

        eventPublisher.publishEvent(new ResourceCreated(this, response, created.getId()));
        return convertToDto(created);
    }

    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody AuthorDTO authorDto) {
        Author author = convertToEntity(authorDto);
        service.save(author);
    }

    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    public AuthorDTO convertToDto(Author author) {
        return authorMapper.authorToAuthorDTO(author);
    }

    public Author convertToEntity(AuthorDTO authorDto){
        return authorMapper.authorDTOToAuthor(authorDto);
    }
}
