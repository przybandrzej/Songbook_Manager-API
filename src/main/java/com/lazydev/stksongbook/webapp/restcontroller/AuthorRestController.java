package com.lazydev.stksongbook.webapp.restcontroller;

import com.lazydev.stksongbook.webapp.dto.AuthorDTO;
import com.lazydev.stksongbook.webapp.service.AuthorService;
import com.lazydev.stksongbook.webapp.model.Author;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private AuthorService manager;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<AuthorDTO> getAll(){
        List<Author> list = (List<Author>) manager.findAll();
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public AuthorDTO getById(@PathVariable("id") Long id) {
        Optional<Author> optAuthor = manager.findById(id);
        if(optAuthor.isPresent()) return convertToDto(optAuthor.get());
        else return null;
    }

    @GetMapping("/name/{name}")
    @ResponseBody
    public List<AuthorDTO> getByName(@PathVariable("name") String name){
        List<Author> list = (List<Author>) manager.findByName(name);
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO create(@RequestBody AuthorDTO authorDto) {
        Author author = convertToEntity(authorDto);
        Author created = manager.save(author);
        return convertToDto(created);
    }

    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody AuthorDTO authorDto) {
        /*Author author = convertToEntity(authorDto);
        manager.update(author);*/
    }

    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable("id") Long id) {
        manager.deleteById(id);
    }

    private AuthorDTO convertToDto(Author author) {
        AuthorDTO authorDto = modelMapper.map(author, AuthorDTO.class);
        return authorDto;
    }

    private Author convertToEntity(AuthorDTO authorDto){
        Author author = modelMapper.map(authorDto, Author.class);
        return author;
    }
}
