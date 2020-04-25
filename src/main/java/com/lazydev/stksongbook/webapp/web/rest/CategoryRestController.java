package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.Category;
import com.lazydev.stksongbook.webapp.service.CategoryService;
import com.lazydev.stksongbook.webapp.service.dto.CategoryDTO;
import com.lazydev.stksongbook.webapp.service.dto.SongDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.mappers.CategoryMapper;
import com.lazydev.stksongbook.webapp.service.mappers.SongMapper;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryRestController {

  private CategoryService service;
  private CategoryMapper modelMapper;
  private SongMapper songMapper;

  @GetMapping
  public ResponseEntity<List<CategoryDTO>> getAll() {
    List<CategoryDTO> list = service.findAll().stream().map(modelMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<CategoryDTO> getById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(modelMapper.map(service.findById(id)), HttpStatus.OK);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<List<CategoryDTO>> getByName(@PathVariable("name") String name) {
    List<CategoryDTO> list = service.findByName(name).stream().map(modelMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/id/{id}/songs")
  public ResponseEntity<List<SongDTO>> getSongsByCategoryId(@PathVariable("id") Long id) {
    var tmp = service.findById(id);
    List<SongDTO> list = tmp.getSongs().stream().map(songMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<CategoryDTO> create(@RequestBody UniversalCreateDTO categoryDto) {
    var category = modelMapper.map(categoryDto);
    category.setId(Constants.DEFAULT_ID);
    var saved = service.save(category);
    return new ResponseEntity<>(modelMapper.map(saved), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO categoryDto) {
    if(service.findByIdNoException(categoryDto.getId()).isEmpty()) {
      throw new EntityNotFoundException(Category.class, categoryDto.getId());
    }
    var category = modelMapper.map(categoryDto);
    var saved = service.save(category);
    return new ResponseEntity<>(modelMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/id/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
