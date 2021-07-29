package com.peopleflow.producerapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandling {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> onException(Exception exc) {
    log.error(exc.getMessage(), exc);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exc.getMessage());
  }

}
