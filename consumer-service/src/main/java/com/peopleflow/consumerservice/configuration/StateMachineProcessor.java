package com.peopleflow.consumerservice.configuration;

import common.EmployeeDTO;

public interface StateMachineProcessor {

   void processMessage(EmployeeDTO employeeDTO);

}
