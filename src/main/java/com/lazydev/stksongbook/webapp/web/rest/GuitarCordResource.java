package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.service.GuitarCordService;
import com.lazydev.stksongbook.webapp.service.dto.GuitarCordDTO;
import com.lazydev.stksongbook.webapp.service.mappers.GuitarCordMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/guitar-cords")
@AllArgsConstructor
public class GuitarCordResource {

  private final Logger log = LoggerFactory.getLogger(GuitarCordResource.class);

  private final GuitarCordService service;
  private final GuitarCordMapper mapper;

  @GetMapping
  public ResponseEntity<List<GuitarCordDTO>> getAllGuitarCords() {
    List<GuitarCordDTO> list = service.findAll().stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GuitarCordDTO> getGuitarCordById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(mapper.map(service.findById(id)), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<GuitarCordDTO> updateGuitarCord(@RequestBody GuitarCordDTO guitarCordDTO) {
    log.debug("Request to update guitarCord {}", guitarCordDTO);
    return new ResponseEntity<>(mapper.map(service.update(guitarCordDTO)), HttpStatus.OK);
  }
}
