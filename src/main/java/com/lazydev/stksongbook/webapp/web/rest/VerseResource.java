package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.service.VerseService;
import com.lazydev.stksongbook.webapp.service.dto.LineDTO;
import com.lazydev.stksongbook.webapp.service.dto.VerseDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateLineDTO;
import com.lazydev.stksongbook.webapp.service.mappers.LineMapper;
import com.lazydev.stksongbook.webapp.service.mappers.VerseMapper;
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
@RequestMapping("/api/verses")
@AllArgsConstructor
public class VerseResource {

  private final Logger log = LoggerFactory.getLogger(VerseResource.class);

  private final VerseService service;
  private final VerseMapper mapper;
  private final LineMapper lineMapper;

  @GetMapping
  public ResponseEntity<List<VerseDTO>> getAllVerses() {
    List<VerseDTO> list = service.findAll().stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<VerseDTO> getVerseById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(mapper.map(service.findById(id)), HttpStatus.OK);
  }

  @GetMapping("/{id}/lines")
  public ResponseEntity<List<LineDTO>> getVerseLines(@PathVariable("id") Long id) {
    return new ResponseEntity<>(service.findLines(id).stream().map(lineMapper::map)
        .collect(Collectors.toList()), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<VerseDTO> updateVerse(@RequestBody VerseDTO verseDTO) {
    log.debug("Request to update verse {}", verseDTO);
    return new ResponseEntity<>(mapper.map(service.update(verseDTO)), HttpStatus.OK);
  }

  @PatchMapping("/{id}/add-line")
  public ResponseEntity<Void> addLine(@PathVariable Long id, @RequestBody @Valid CreateLineDTO line) {
    log.debug("Request to add line {} to verse {}", line, id);
    service.addLine(id, line);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/remove-line/{lineId}")
  public ResponseEntity<Void> removeLine(@PathVariable Long id, @PathVariable Long lineId) {
    log.debug("Request to remove line {} from verse {}", lineId, id);
    service.removeLine(id, lineId);
    return ResponseEntity.noContent().build();
  }
}
