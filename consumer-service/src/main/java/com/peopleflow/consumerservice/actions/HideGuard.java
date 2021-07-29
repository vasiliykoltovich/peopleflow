package com.peopleflow.consumerservice.actions;

import static common.EmployeeConstants.EMPLOYEE;

import com.peopleflow.consumerservice.data.Employee;
import com.peopleflow.consumerservice.service.EmployeeService;
import com.peopleflow.consumerservice.utils.StateMachineUtil;
import common.EmployeeDTO;
import common.EmployeeEvent;
import common.EmployeeState;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
@Slf4j
@AllArgsConstructor
public class HideGuard implements Guard<EmployeeState, EmployeeEvent> {
 private EmployeeService employeeService;

  @Override
  public boolean evaluate(StateContext<EmployeeState, EmployeeEvent> stateContext) {
    var employee = Objects
        .requireNonNullElse(StateMachineUtil.getEmployee(stateContext.getStateMachine()),
            null);
    var employeeId = Objects.isNull(employee)? null : employee.getEmployeeId();
    return Objects.nonNull(employeeId) && Objects.nonNull(employeeService.get(employeeId));

  }
}
