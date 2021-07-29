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
public class InitAction implements Action<EmployeeState, EmployeeEvent> {

  private EmployeeService employeeService;

  @Override
  public void execute(final StateContext<EmployeeState, EmployeeEvent> context) {
    EmployeeDTO employeeDTO = StateMachineUtil.getEmployee(context.getStateMachine());
    EmployeeDTO tempEmployeeDTO = employeeDTO.toBuilder().state(EmployeeState.ADDED).build();
    log.info("Create employee '{}'", employeeDTO.getEmployeeId());
    employeeService.init(tempEmployeeDTO);
    log.info("New Employee was added");
  }
}
