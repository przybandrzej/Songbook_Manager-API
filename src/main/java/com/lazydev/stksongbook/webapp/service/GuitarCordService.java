package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.GuitarCord;
import com.lazydev.stksongbook.webapp.repository.GuitarCordRepository;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GuitarCordService {

  private final GuitarCordRepository guitarCordRepository;

  public List<GuitarCord> findAll() {
    return guitarCordRepository.findAll();
  }

  public List<GuitarCord> findAll(int limit) {
    return guitarCordRepository.findAll(PageRequest.of(0, limit)).toList();
  }

  public Optional<GuitarCord> findByIdNoException(Long id) {
    return guitarCordRepository.findById(id);
  }

  public GuitarCord findById(Long id) {
    return guitarCordRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(GuitarCord.class, id));
  }

  public GuitarCord save(GuitarCord saveGuitarCord) {
    return guitarCordRepository.save(saveGuitarCord);
  }

  public void deleteById(Long id) {
    var guitarCord = findById(id);
    guitarCordRepository.delete(guitarCord);
  }
}
