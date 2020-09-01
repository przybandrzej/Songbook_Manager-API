package com.lazydev.stksongbook.webapp.mailer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessage {

  private String to;
  private String subject;
  private String text;
  private String html;
}
