package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.*;
import com.lazydev.stksongbook.webapp.repository.SongEditRepository;
import com.lazydev.stksongbook.webapp.repository.VerseRepository;
import com.lazydev.stksongbook.webapp.security.UserContextService;
import com.lazydev.stksongbook.webapp.service.dto.VerseDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateLineDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateVerseDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.exception.ForbiddenOperationException;
import com.lazydev.stksongbook.webapp.util.Constants;
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
@Validated
@Transactional
public class VerseService {

  @Value("${spring.flyway.placeholders.role.superuser}")
  private String superuserRoleName;
  @Value("${spring.flyway.placeholders.role.admin}")
  private String adminRoleName;
  @Value("${spring.flyway.placeholders.role.moderator}")
  private String moderatorRoleName;

  private final VerseRepository verseRepository;
  private final LineService lineService;
  private final UserContextService userContextService;
  private final SongEditRepository songEditRepository;

  public VerseService(VerseRepository verseRepository, LineService lineService, UserContextService userContextService, SongEditRepository songEditRepository) {
    this.verseRepository = verseRepository;
    this.lineService = lineService;
    this.userContextService = userContextService;
    this.songEditRepository = songEditRepository;
  }

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

  public Set<Line> findLines(Long id) {
    return findById(id).getLines();
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

  public Verse update(@Valid VerseDTO dto) {
    User currentUser = userContextService.getCurrentUser();
    Verse verse = verseRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException(Verse.class, dto.getId()));
    Song song = verse.getSong();
    filterRequestForApprovedSong(song, currentUser, "updated");
    verse.setChorus(dto.isChorus());
    verse.setOrder(dto.getOrder());
    Verse saved = verseRepository.save(verse);
    SongEdit edit = new SongEdit();
    edit.setId(Constants.DEFAULT_ID);
    currentUser.addEditedSong(edit);
    song.addEdit(edit);
    songEditRepository.save(edit);
    return saved;
  }

  public Line addLine(Long id, CreateLineDTO line) {
    User currentUser = userContextService.getCurrentUser();
    Verse verse = verseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Verse.class, id));
    Song song = verse.getSong();
    filterRequestForApprovedSong(song, currentUser, "updated");
    Line created = lineService.create(line, verse);
    SongEdit edit = new SongEdit();
    edit.setId(Constants.DEFAULT_ID);
    currentUser.addEditedSong(edit);
    song.addEdit(edit);
    songEditRepository.save(edit);
    return created;
  }

  public void removeLine(Long verseId, Long lineId) {
    User currentUser = userContextService.getCurrentUser();
    Verse verse = verseRepository.findById(verseId).orElseThrow(() -> new EntityNotFoundException(Verse.class, verseId));
    Song song = verse.getSong();
    filterRequestForApprovedSong(song, currentUser, "updated");
    lineService.deleteById(lineId);
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
