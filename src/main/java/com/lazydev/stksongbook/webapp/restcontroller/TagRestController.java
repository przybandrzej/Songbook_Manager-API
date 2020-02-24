package com.lazydev.stksongbook.webapp.restcontroller;

import com.lazydev.stksongbook.webapp.dto.TagDTO;
import com.lazydev.stksongbook.webapp.model.Tag;
import com.lazydev.stksongbook.webapp.service.TagService;
import com.lazydev.stksongbook.webapp.model.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tags")
public class TagRestController {

    @Autowired
    private TagService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<TagDTO> getAll(){
        List<Tag> list = (List<Tag>) service.findAll();;
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public TagDTO getById(@PathVariable("id") Long id) {
        Optional<Tag> tag = service.findById(id);;
        if(tag.isPresent()) return convertToDto(tag.get());
        else return null;
    }

    @GetMapping("/name/{name}")
    public List<TagDTO> getByName(@PathVariable("name") String name){
        List<Tag> list = (List<Tag>) service.findByName(name);
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO create(@RequestBody Tag obj) {
        return service.save(obj);
    }

    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Tag obj) {
        return service.save(obj);
    }*/

    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    public TagDTO convertToDto(Tag tag) {
        /*PropertyMap<Tag, TagDTO> personMap = new PropertyMap<Tag, TagDTO>() {
            protected void configure() {
                map().setTagRole(source.getTagRole().getName());
                if (tag.getFirstName() != null) { map().setFirstName(source.getFirstName()); }
                else { map().setFirstName(""); }
                if (tag.getLastName() != null) { map().setLastName(source.getLastName()); }
                else { map().setLastName(""); }
            }
        };
        modelMapper.addMappings(personMap);*/
        TagDTO tagDto = modelMapper.map(tag, TagDTO.class);
        return tagDto;
    }

    //TODO
    public Tag convertToEntity(TagDTO tagDto) {
        /*PropertyMap<TagDTO, Tag> personMap = new PropertyMap<TagDTO, Tag>() {
            protected void configure() {
                map().setTagRole(source.getTagRole());
                if (tag.getFirstName() != null) { map().setFirstName(source.getFirstName()); }
                else { map().setFirstName(""); }
                if (tag.getLastName() != null) { map().setLastName(source.getLastName()); }
                else { map().setLastName(""); }
            }
        };
        modelMapper.addMappings(personMap);*/
        Tag tag = modelMapper.map(tagDto, Tag.class);
        return tag;
    }
}
