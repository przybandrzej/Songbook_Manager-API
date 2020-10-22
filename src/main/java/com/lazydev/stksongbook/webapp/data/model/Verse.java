package com.lazydev.stksongbook.webapp.data.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "verses")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"lines"})
public class Verse {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "is_chorus", nullable = false)
  private boolean isChorus = false;

  @Column(name = "verse_order", nullable = false)
  private Long order;

  @OneToMany(mappedBy = "verse", orphanRemoval = true)
  private Set<Line> lines = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "song_id", referencedColumnName = "id", nullable = false)
  private Song song;

  public boolean addLine(Line line) {
    if(this.lines.add(line)) {
      line.setVerse(this);
      return true;
    }
    return false;
  }

  public boolean removeLine(Line line) {
    if(this.lines.remove(line)) {
      line.setVerse(null);
      return true;
    }
    return false;
  }

  public Long getOrder() {
    return order;
  }

  public void setOrder(Long order) {
    this.order = order;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean isChorus() {
    return isChorus;
  }

  public void setChorus(boolean chorus) {
    isChorus = chorus;
  }

  public Set<Line> getLines() {
    return lines;
  }

  public void setLines(Set<Line> lines) {
    this.lines = lines;
  }

  public Song getSong() {
    return song;
  }

  public void setSong(Song song) {
    this.song = song;
  }
}
