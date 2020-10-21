package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.Verse;
import com.lazydev.stksongbook.webapp.repository.VerseRepository;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateLineDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateVerseDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Validated
public class VerseService {

  private final VerseRepository verseRepository;
  private final LineService lineService;

  public List<Verse> findAll() {
    return verseRepository.findAll();
  }

  public List<Verse> findAll(int limit) {
    return verseRepository.findAll(PageRequest.of(0, limit)).toList();
  }

  public Optional<Verse> findByIdNoException(Long id) {
    return verseRepository.findById(id);
  }

  public Verse findById(Long id) {
    return verseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Verse.class, id));
  }

  public Verse save(Verse saveVerse) {
    return verseRepository.save(saveVerse);
  }

  public void deleteById(Long id) {
    var verse = findById(id);
    lineService.deleteAll(verse.getLines());
    verseRepository.deleteById(id);
  }

  public void deleteAll(Collection<Verse> list) {
    for(Verse verse : list) {
      var found = findById(verse.getId());
      lineService.deleteAll(found.getLines());
    }
    verseRepository.deleteAll(list);
  }

  public Verse create(@Valid CreateVerseDTO dto, Song song) {
    Verse verse = new Verse();
    verse.setId(Constants.DEFAULT_ID);
    verse.setChorus(dto.isChorus());
    verse.setOrder(dto.getOrder());
    song.addVerse(verse);
    Verse saved = verseRepository.save(verse);
    for(CreateLineDTO line : dto.getLines()) {
      lineService.create(line, verse);
    }
    return saved;
  }
}
