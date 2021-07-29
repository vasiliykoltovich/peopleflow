package com.peopleflow.consumerservice.persister;

import common.EmployeeEvent;
import common.EmployeeState;
import java.util.HashMap;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

@Service
public class EmployeeStateMachinePersister implements StateMachinePersist<EmployeeState, EmployeeEvent, String> {

  private final HashMap<String, StateMachineContext<EmployeeState, EmployeeEvent>> contexts = new HashMap<>();

  @Override
  public void write(final StateMachineContext<EmployeeState, EmployeeEvent> context, final String contextObj) {
    contexts.put(contextObj, context);
  }

  @Override
  public StateMachineContext<EmployeeState, EmployeeEvent> read(final String contextObj) {
    return contexts.containsKey(contextObj) ?
        contexts.get(contextObj) :
        new DefaultStateMachineContext<>(EmployeeState.INITIAL, null, null, null, null, "employeeStateMachine");

  }
}
