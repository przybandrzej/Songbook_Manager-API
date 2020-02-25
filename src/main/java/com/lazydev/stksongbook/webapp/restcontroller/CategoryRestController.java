package com.lazydev.stksongbook.webapp.restcontroller;

import com.lazydev.stksongbook.webapp.dto.CategoryDTO;
import com.lazydev.stksongbook.webapp.dto.CategoryMapper;
import com.lazydev.stksongbook.webapp.service.CategoryService;
import com.lazydev.stksongbook.webapp.model.Category;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryRestController {

    @Autowired
    private CategoryService service;

    @Autowired
    private CategoryMapper modelMapper;

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
