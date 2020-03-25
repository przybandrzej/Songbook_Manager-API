package com.lazydev.stksongbook.webapp.api.restcontroller;

import com.lazydev.stksongbook.webapp.api.dto.CategoryDTO;
import com.lazydev.stksongbook.webapp.api.mappers.CategoryMapper;
import com.lazydev.stksongbook.webapp.data.model.Category;
import com.lazydev.stksongbook.webapp.data.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/categories")
public class CategoryRestController {

    private CategoryService service;
    private CategoryMapper modelMapper;

    public CategoryRestController(CategoryService service, CategoryMapper mapper) {
        this.service = service;
        this.modelMapper = mapper;
    }

    @GetMapping
    public List<CategoryDTO> getAll(){
        List<Category> categories = (List<Category>) service.findAll();
        return categories.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public CategoryDTO getById(@PathVariable("id") Long id) {
        Optional<Category> object =  service.findById(id);
        return object.map(this::convertToDto).orElse(null);
    }

    @GetMapping("/name/{name}")
    public List<CategoryDTO> getByName(@PathVariable("name") String name){
        List<Category> list = (List<Category>) service.findByName(name);
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}/songs")
    public List<CategoryDTO> getCategorySongs(@PathVariable("id") Long id){
        //Todo
        return Collections.emptyList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO create(@RequestBody CategoryDTO categoryDto) {
        return convertToDto(service.save(convertToEntity(categoryDto)));
    }

    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody CategoryDTO categoryDto) {
        service.save(convertToEntity(categoryDto));
    }

    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    public CategoryDTO convertToDto(Category category) {
        return modelMapper.categoryToCategoryDTO(category);
    }

    public Category convertToEntity(CategoryDTO categoryDto) {
        return modelMapper.categoryDTOToCategory(categoryDto);
    }
}
