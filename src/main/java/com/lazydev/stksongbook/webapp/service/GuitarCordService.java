package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.*;
import com.lazydev.stksongbook.webapp.repository.GuitarCordRepository;
import com.lazydev.stksongbook.webapp.repository.SongEditRepository;
import com.lazydev.stksongbook.webapp.security.UserContextService;
import com.lazydev.stksongbook.webapp.service.dto.GuitarCordDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateGuitarCordDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.exception.ForbiddenOperationException;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@Transactional
public class GuitarCordService {

  @Value("${spring.flyway.placeholders.role.superuser}")
  private String superuserRoleName;
  @Value("${spring.flyway.placeholders.role.admin}")
  private String adminRoleName;
  @Value("${spring.flyway.placeholders.role.moderator}")
  private String moderatorRoleName;

  private final GuitarCordRepository guitarCordRepository;
  private final UserContextService userContextService;
  private final SongEditRepository songEditRepository;

  public GuitarCordService(GuitarCordRepository guitarCordRepository, UserContextService userContextService, SongEditRepository songEditRepository) {
    this.guitarCordRepository = guitarCordRepository;
    this.userContextService = userContextService;
    this.songEditRepository = songEditRepository;
  }

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

  public void deleteAll(Collection<GuitarCord> list) {
    List<GuitarCord> toDelete = new ArrayList<>();
    for(GuitarCord line : list) {
      toDelete.add(findById(line.getId()));
    }
    guitarCordRepository.deleteAll(toDelete);
  }

  public GuitarCord create(@Valid CreateGuitarCordDTO dto, Line line) {
    GuitarCord cord = new GuitarCord();
    cord.setId(Constants.DEFAULT_ID);
    cord.setContent(dto.getContent());
    cord.setPosition(dto.getPosition());
    line.addCord(cord);
    return guitarCordRepository.save(cord);
  }

  public GuitarCord update(@Valid GuitarCordDTO dto) {
    User currentUser = userContextService.getCurrentUser();
    GuitarCord guitarCord = guitarCordRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException(GuitarCord.class, dto.getId()));
    Song song = guitarCord.getLine().getVerse().getSong();
    if(!song.isAwaiting()
        && !(currentUser.getUserRole().getName().equals(superuserRoleName)
        || currentUser.getUserRole().getName().equals(adminRoleName)
        || currentUser.getUserRole().getName().equals(moderatorRoleName))) {
      throw new ForbiddenOperationException("Approved song can be updated only by a moderator or admin.");
    }
    guitarCord.setContent(dto.getContent());
    guitarCord.setPosition(dto.getPosition());
    GuitarCord saved = guitarCordRepository.save(guitarCord);
    SongEdit edit = new SongEdit();
    edit.setId(Constants.DEFAULT_ID);
    currentUser.addEditedSong(edit);
    song.addEdit(edit);
    songEditRepository.save(edit);
    return saved;
  }
}
