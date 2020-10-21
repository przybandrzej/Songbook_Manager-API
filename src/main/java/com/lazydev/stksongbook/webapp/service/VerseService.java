package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Verse;
import com.lazydev.stksongbook.webapp.repository.LineRepository;
import com.lazydev.stksongbook.webapp.repository.VerseRepository;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VerseService {

  private final VerseRepository verseRepository;
  private final LineRepository lineRepository;

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
    lineRepository.deleteAll(verse.getLines());
    verseRepository.deleteById(id);
  }
}
