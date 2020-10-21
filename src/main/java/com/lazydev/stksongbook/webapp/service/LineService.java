package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Line;
import com.lazydev.stksongbook.webapp.repository.GuitarCordRepository;
import com.lazydev.stksongbook.webapp.repository.LineRepository;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LineService {

  private final LineRepository lineRepository;
  private final GuitarCordRepository cordRepository;

  public List<Line> findAll() {
    return lineRepository.findAll();
  }

  public List<Line> findAll(int limit) {
    return lineRepository.findAll(PageRequest.of(0, limit)).toList();
  }

  public Optional<Line> findByIdNoException(Long id) {
    return lineRepository.findById(id);
  }

  public Line findById(Long id) {
    return lineRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Line.class, id));
  }

  public Line save(Line saveLine) {
    return lineRepository.save(saveLine);
  }

  public void deleteById(Long id) {
    var line = findById(id);
    cordRepository.deleteAll(line.getCords());
    lineRepository.deleteById(id);
  }
}
