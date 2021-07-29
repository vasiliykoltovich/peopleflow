package com.peopleflow.consumerservice.utils;

import static common.EmployeeConstants.EMPLOYEE;

import common.EmployeeDTO;
import common.EmployeeEvent;
import common.EmployeeState;
import org.springframework.statemachine.StateMachine;

public class StateMachineUtil {

  public static void putEmployee(StateMachine<EmployeeState, EmployeeEvent> stateMachine, EmployeeDTO employee) {
    stateMachine.getExtendedState().getVariables().put(EMPLOYEE, employee);
  }

  public static EmployeeDTO getEmployee(StateMachine<EmployeeState, EmployeeEvent> stateMachine) {
    Object employee = stateMachine.getExtendedState().getVariables().get(EMPLOYEE);
    return (EmployeeDTO) employee;
  }

}
