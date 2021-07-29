package com.peopleflow.consumerservice.service;

import common.EmployeeDTO;

public interface EmployeeService {

  EmployeeDTO get(String employeeId);

  EmployeeDTO init(EmployeeDTO employeeDTO);

  EmployeeDTO update(EmployeeDTO dto);


}
