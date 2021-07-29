package com.peopleflow.producerapi.controller;


import common.EmployeeDTO;
import common.EmployeeState;

public interface EmployeeService {

  EmployeeDTO addEmployee(EmployeeDTO emp);

  boolean changeStatus(String employeeId, EmployeeState status);

}
