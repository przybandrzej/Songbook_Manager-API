package com.lazydev.stksongbook.webapp.data.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lines")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"cords"})
public class Line {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "content", nullable = false)
  private String content;

  @Column(name = "order", nullable = false)
  private int order;

  @OneToMany(mappedBy = "line", orphanRemoval = true)
  private Set<GuitarCord> cords = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "verse_id", referencedColumnName = "id", nullable = false)
  private Verse verse;

  public boolean addCord(GuitarCord cord) {
    if(this.cords.add(cord)) {
      cord.setLine(this);
      return true;
    }
    return false;
  }

  public boolean removeCord(GuitarCord cord) {
    if(this.cords.remove(cord)) {
      cord.setLine(null);
      return true;
    }
    return false;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Set<GuitarCord> getCords() {
    return cords;
  }

  public void setCords(Set<GuitarCord> cords) {
    this.cords = cords;
  }

  public Verse getVerse() {
    return verse;
  }

  public void setVerse(Verse verse) {
    this.verse = verse;
  }
}
