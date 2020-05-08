package com.lazydev.stksongbook.webapp.web.rest.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Error {

  private HttpStatus status;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;
  private String message;
  private List<SubError> subErrors;

  private Error() {
    timestamp = LocalDateTime.now();
  }

  public Error(HttpStatus status) {
    this();
    this.status = status;
  }

  public Error(HttpStatus status, String message) {
    this();
    this.status = status;
    this.message = message;
  }

  public Error(HttpStatus status, String message, List<SubError> errors) {
    this();
    this.status = status;
    this.message = message;
    this.subErrors = errors;
  }
}
