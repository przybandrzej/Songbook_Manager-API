package com.lazydev.stksongbook.webapp.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenDTO {

  @JsonProperty("idToken")
  private String idToken;

  public TokenDTO(String idToken) {
    this.idToken = idToken;
  }

  public String getIdToken() {
    return idToken;
  }

  public void setIdToken(String idToken) {
    this.idToken = idToken;
  }
}
