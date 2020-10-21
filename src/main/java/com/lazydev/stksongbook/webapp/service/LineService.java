package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Line;
import com.lazydev.stksongbook.webapp.data.model.Verse;
import com.lazydev.stksongbook.webapp.repository.LineRepository;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateGuitarCordDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateLineDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LineService {

  private final LineRepository lineRepository;
  private final GuitarCordService cordService;

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
    cordService.deleteAll(line.getCords());
    lineRepository.deleteById(id);
  }

  public void deleteAll(Collection<Line> list) {
    for(Line line : list) {
      var found = findById(line.getId());
      cordService.deleteAll(found.getCords());
    }
    lineRepository.deleteAll(list);
  }

  public Line create(@Valid CreateLineDTO dto, Verse verse) {
    Line line = new Line();
    line.setId(Constants.DEFAULT_ID);
    line.setOrder(dto.getOrder());
    line.setContent(dto.getContent());
    verse.addLine(line);
    Line saved = lineRepository.save(line);
    for(CreateGuitarCordDTO cord : dto.getCords()) {
      cordService.create(cord, saved);
    }
    return saved;
  }
}
