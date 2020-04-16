package com.lazydev.stksongbook.webapp.api.restcontroller;

import com.lazydev.stksongbook.webapp.api.dto.SongDTO;
import com.lazydev.stksongbook.webapp.api.dto.TagDTO;
import com.lazydev.stksongbook.webapp.api.mappers.SongMapper;
import com.lazydev.stksongbook.webapp.api.mappers.TagMapper;
import com.lazydev.stksongbook.webapp.data.model.Tag;
import com.lazydev.stksongbook.webapp.data.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/tags")
@AllArgsConstructor
public class TagRestController {

    private TagService service;
    private TagMapper modelMapper;
    private SongMapper songMapper;

    @GetMapping
    public List<TagDTO> getAll(){
        return service.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public TagDTO getById(@PathVariable("id") Long id) {
        return service.findById(id).map(this::convertToDto).orElse(null);
    }

    @GetMapping("/name/{name}")
    public List<TagDTO> getByName(@PathVariable("name") String name){
        return service.findByName(name).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}/songs")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<SongDTO> getByTagId(@PathVariable("id") Long id) {
        return service.findById(id)
            .map(tag -> tag.getSongs().stream().map(songMapper::songToSongDTO).collect(Collectors.toList()))
            .orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO create(@RequestBody TagDTO obj) {
        return convertToDto(service.save(convertToEntity(obj)));
    }

    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody TagDTO obj) {
        service.save(convertToEntity(obj));
    }

    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    public TagDTO convertToDto(Tag tag) {
        return modelMapper.tagToTagDTO(tag);
    }

    public Tag convertToEntity(TagDTO tagDto) {
        return modelMapper.tagDTOToTag(tagDto);
    }
}
