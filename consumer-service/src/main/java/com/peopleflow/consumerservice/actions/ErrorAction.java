package com.peopleflow.consumerservice.actions;

import common.EmployeeEvent;
import common.EmployeeState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@Slf4j
public class ErrorAction implements Action<EmployeeState, EmployeeEvent> {
  @Override
  public void execute(final StateContext<EmployeeState, EmployeeEvent> context) {
    log.info("Error in status change: {}", context.getTarget().getId());
  }
}
