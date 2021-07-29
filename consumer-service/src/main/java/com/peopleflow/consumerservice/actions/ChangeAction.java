package com.peopleflow.consumerservice.actions;

import static common.EmployeeConstants.EMPLOYEE;

import com.peopleflow.consumerservice.service.EmployeeService;
import com.peopleflow.consumerservice.utils.StateMachineUtil;
import common.EmployeeDTO;
import common.EmployeeEvent;
import common.EmployeeState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@Slf4j
@AllArgsConstructor
public class ChangeAction implements Action<EmployeeState, EmployeeEvent> {

  private EmployeeService employeeService;

  @Override
  public void execute(StateContext<EmployeeState, EmployeeEvent> stateContext) {
    EmployeeDTO employeeDTO = StateMachineUtil.getEmployee(stateContext.getStateMachine());
    EmployeeDTO tempEmployeeDTO = employeeDTO.toBuilder().state(getTargetState(stateContext)).build();
    employeeService.update(tempEmployeeDTO);
  }

  public EmployeeState getTargetState(StateContext<EmployeeState, EmployeeEvent> stateContext) {
    return stateContext.getTarget().getId();
  }
}
