package com.peopleflow.consumerservice.configuration;

import common.EmployeeEvent;
import common.EmployeeState;
import org.springframework.statemachine.StateMachine;

public interface StateMachineStateStorage {

  StateMachine<EmployeeState, EmployeeEvent> getState(String employeeId);

  void saveState(String employeeId, StateMachine<EmployeeState, EmployeeEvent> stateMachine);
}
