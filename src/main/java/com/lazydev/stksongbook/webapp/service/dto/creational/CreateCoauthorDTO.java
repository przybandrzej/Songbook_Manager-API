package com.lazydev.stksongbook.webapp.service.dto.creational;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateCoauthorDTO {

  String authorName;
  String function;
}
