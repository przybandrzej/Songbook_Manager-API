package com.lazydev.stksongbook.webapp.mailer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailMessage {

  private final String text;
  private final int priority;
  private final boolean secret;

  public EmailMessage(@JsonProperty("text") String text,
                       @JsonProperty("priority") int priority,
                       @JsonProperty("secret") boolean secret) {
    this.text = text;
    this.priority = priority;
    this.secret = secret;
  }

  public String getText() {
    return text;
  }

  public int getPriority() {
    return priority;
  }

  public boolean isSecret() {
    return secret;
  }

  @Override
  public String toString() {
    return "EmailMessage {" +
        "text='" + text + "'" +
    ", priority=" + priority +
        ", secret=" + secret +
        "}";
  }
}
