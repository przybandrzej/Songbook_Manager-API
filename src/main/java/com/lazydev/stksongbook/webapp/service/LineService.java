package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.*;
import com.lazydev.stksongbook.webapp.repository.LineRepository;
import com.lazydev.stksongbook.webapp.repository.SongEditRepository;
import com.lazydev.stksongbook.webapp.security.UserContextService;
import com.lazydev.stksongbook.webapp.service.dto.LineDTO;
import com.lazydev.stksongbook.webapp.service.dto.VerseDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateGuitarCordDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateLineDTO;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@Validated
public class LineService {

  @Value("${spring.flyway.placeholders.role.superuser}")
  private String superuserRoleName;
  @Value("${spring.flyway.placeholders.role.admin}")
  private String adminRoleName;
  @Value("${spring.flyway.placeholders.role.moderator}")
  private String moderatorRoleName;

  private final LineRepository lineRepository;
  private final GuitarCordService cordService;
  private final UserContextService userContextService;
  private final SongEditRepository songEditRepository;

  public LineService(LineRepository lineRepository, GuitarCordService cordService, UserContextService userContextService, SongEditRepository songEditRepository) {
    this.lineRepository = lineRepository;
    this.cordService = cordService;
    this.userContextService = userContextService;
    this.songEditRepository = songEditRepository;
  }

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

  public Set<GuitarCord> findCordsById(Long id) {
    return findById(id).getCords();
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

  public Line update(@Valid LineDTO dto) {
    User currentUser = userContextService.getCurrentUser();
    Line line = lineRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException(Line.class, dto.getId()));
    Song song = line.getVerse().getSong();
    filterRequestForApprovedSong(song, currentUser, "updated");
    line.setOrder(dto.getOrder());
    line.setContent(dto.getContent());
    Line saved = lineRepository.save(line);
    SongEdit edit = new SongEdit();
    edit.setId(Constants.DEFAULT_ID);
    currentUser.addEditedSong(edit);
    song.addEdit(edit);
    songEditRepository.save(edit);
    return saved;
  }

  public void addCord(Long id, CreateGuitarCordDTO cordDTO) {
    User currentUser = userContextService.getCurrentUser();
    Line line = lineRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Line.class, id));
    Song song = line.getVerse().getSong();
    filterRequestForApprovedSong(song, currentUser, "updated");
    cordService.create(cordDTO, line);
    SongEdit edit = new SongEdit();
    edit.setId(Constants.DEFAULT_ID);
    currentUser.addEditedSong(edit);
    song.addEdit(edit);
    songEditRepository.save(edit);
  }

  public void removeCord(Long lineId, Long cordId) {
    User currentUser = userContextService.getCurrentUser();
    Line line = lineRepository.findById(lineId).orElseThrow(() -> new EntityNotFoundException(Line.class, lineId));
    Song song = line.getVerse().getSong();
    filterRequestForApprovedSong(song, currentUser, "updated");
    cordService.deleteById(cordId);
    SongEdit edit = new SongEdit();
    edit.setId(Constants.DEFAULT_ID);
    currentUser.addEditedSong(edit);
    song.addEdit(edit);
    songEditRepository.save(edit);
  }

  private void filterRequestForApprovedSong(Song song, User user, String option) {
    if(!song.isAwaiting()
        && !(user.getUserRole().getName().equals(superuserRoleName)
        || user.getUserRole().getName().equals(adminRoleName)
        || user.getUserRole().getName().equals(moderatorRoleName))) {
      throw new ForbiddenOperationException("Approved song can be " + option + " only by a moderator or admin.");
    }
  }
}
