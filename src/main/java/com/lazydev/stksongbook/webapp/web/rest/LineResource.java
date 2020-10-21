package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.service.LineService;
import com.lazydev.stksongbook.webapp.service.dto.GuitarCordDTO;
import com.lazydev.stksongbook.webapp.service.dto.LineDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateGuitarCordDTO;
import com.lazydev.stksongbook.webapp.service.mappers.GuitarCordMapper;
import com.lazydev.stksongbook.webapp.service.mappers.LineMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lines")
@AllArgsConstructor
public class LineResource {

  private final Logger log = LoggerFactory.getLogger(LineResource.class);

  private final LineService service;
  private final LineMapper mapper;
  private final GuitarCordMapper cordMapper;

  @GetMapping
  public ResponseEntity<List<LineDTO>> getAllLines() {
    List<LineDTO> list = service.findAll().stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<LineDTO> getLineById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(mapper.map(service.findById(id)), HttpStatus.OK);
  }

  @GetMapping("/{id}/guitar-cords")
  public ResponseEntity<List<GuitarCordDTO>> getLineCords(@PathVariable("id") Long id) {
    return new ResponseEntity<>(service.findCordsById(id).stream().map(cordMapper::map)
        .collect(Collectors.toList()), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<LineDTO> updateLine(@RequestBody LineDTO lineDTO) {
    log.debug("Request to update line {}", lineDTO);
    return new ResponseEntity<>(mapper.map(service.update(lineDTO)), HttpStatus.OK);
  }

  @PatchMapping("/{id}/add-guitar-cord")
  public ResponseEntity<Void> addGuitarCord(@PathVariable Long id, @RequestBody @Valid CreateGuitarCordDTO cord) {
    log.debug("Request to add guitar cord {} to line {}", cord, id);
    service.addCord(id, cord);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/remove-guitar-cord/{cordId}")
  public ResponseEntity<Void> removeGuitarCord(@PathVariable Long id, @PathVariable Long cordId) {
    log.debug("Request to remove guitar cord {} from line {}", cordId, id);
    service.removeCord(id, cordId);
    return ResponseEntity.noContent().build();
  }
}
