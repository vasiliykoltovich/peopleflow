package com.peopleflow.consumerservice.exceptions;

public class EmployeeServiceException extends RuntimeException{

  public EmployeeServiceException() {
    super();
  }

  public EmployeeServiceException(String message) {
    super(message);
  }

  public EmployeeServiceException(Throwable cause) {
    super(cause);
  }
}
