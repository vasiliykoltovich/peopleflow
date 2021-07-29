package com.peopleflow.producerapi.exceptions;

import common.EmployeeState;

public class EmployeeStatusUpdateException extends RuntimeException{

  private String employeeId;
  private EmployeeState status;

  public EmployeeStatusUpdateException(String employeeId, EmployeeState status) {
    super("New status:" + status + " is invalid for employeeId:" + employeeId);
  }
}
