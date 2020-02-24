package com.lazydev.stksongbook.webapp.restcontroller;

import com.lazydev.stksongbook.webapp.dto.CategoryDTO;
import com.lazydev.stksongbook.webapp.service.CategoryService;
import com.lazydev.stksongbook.webapp.model.Category;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    @GetMapping
    public List<CategoryDTO> getAll(){
        List<Category> categories = (List<Category>) service.findAll();
        return categories.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public CategoryDTO getById(@PathVariable("id") Long id) {
        Optional<Category> object =  service.findById(id);
        if(object.isPresent()) return convertToDto(object.get());
        else return null;
    }

    @GetMapping("/name/{name}")
    public List<CategoryDTO> getByName(@PathVariable("name") String name){
        List<Category> list = (List<Category>) service.findByName(name);
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO create(@RequestBody Category category) {
        return service.save(category);
    }

    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Category category) {
        return service.save(category);
    }*/

    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    public CategoryDTO convertToDto(Category category) {
        /*PropertyMap<User, UserDTO> personMap = new PropertyMap<User, UserDTO>() {
            protected void configure() {
                map().setUserRole(source.getUserRole().getName());
                if (user.getFirstName() != null) { map().setFirstName(source.getFirstName()); }
                else { map().setFirstName(""); }
                if (user.getLastName() != null) { map().setLastName(source.getLastName()); }
                else { map().setLastName(""); }
            }
        };
        modelMapper.addMappings(personMap);*/
        CategoryDTO categoryDto = modelMapper.map(category, CategoryDTO.class);
        return categoryDto;
    }

    //TODO
    public Category convertToEntity(CategoryDTO categoryDto) {
        /*PropertyMap<UserDTO, User> personMap = new PropertyMap<UserDTO, User>() {
            protected void configure() {
                map().setUserRole(source.getUserRole());
                if (user.getFirstName() != null) { map().setFirstName(source.getFirstName()); }
                else { map().setFirstName(""); }
                if (user.getLastName() != null) { map().setLastName(source.getLastName()); }
                else { map().setLastName(""); }
            }
        };
        modelMapper.addMappings(personMap);*/
        Category category = modelMapper.map(categoryDto, Category.class);
        return category;
    }
}
