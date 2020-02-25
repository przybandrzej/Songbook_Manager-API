package com.lazydev.stksongbook.webapp.restcontroller;

import com.lazydev.stksongbook.webapp.dto.AuthorDTO;
import com.lazydev.stksongbook.webapp.dto.AuthorMapper;
import com.lazydev.stksongbook.webapp.service.AuthorService;
import com.lazydev.stksongbook.webapp.model.Author;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorRestController {

    @Autowired
    private AuthorService service;

    @GetMapping
    public List<AuthorDTO> getAll(){
        List<Author> list = (List<Author>) service.findAll();
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public AuthorDTO getById(@PathVariable("id") Long id) {
        Optional<Author> optAuthor = service.findById(id);
        return optAuthor.map(this::convertToDto).orElse(null);
    }

    @GetMapping("/name/{name}")
    @ResponseBody
    public List<AuthorDTO> getByName(@PathVariable("name") String name){
        List<Author> list = (List<Author>) service.findByName(name);
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO create(@RequestBody AuthorDTO authorDto) {
        Author author = convertToEntity(authorDto);
        Author created = service.save(author);
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
        return AuthorMapper.INSTANCE.authorToAuthorDTO(author);
    }

    public Author convertToEntity(AuthorDTO authorDto){
        return AuthorMapper.INSTANCE.authorDTOToAuthor(authorDto);
    }
}
