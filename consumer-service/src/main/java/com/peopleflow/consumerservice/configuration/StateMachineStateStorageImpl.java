package com.peopleflow.consumerservice.configuration;

import common.EmployeeEvent;
import common.EmployeeState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class StateMachineStateStorageImpl implements StateMachineStateStorage {

  private final StateMachinePersister<EmployeeState, EmployeeEvent, String> persister;

  private final StateMachineFactory<EmployeeState, EmployeeEvent> stateMachineFactory;

  @Override
  public StateMachine<EmployeeState, EmployeeEvent> getState(String employeeId) {
    StateMachine<EmployeeState, EmployeeEvent> stateMachine = stateMachineFactory.getStateMachine(employeeId);
    try {
      return persister.restore(stateMachine, employeeId);
    } catch (Exception exc) {
      throw new RuntimeException(exc);
    }
  }

  @Override
  public void saveState(String employeeId, StateMachine<EmployeeState, EmployeeEvent> stateMachine) {
    try {
      persister.persist(stateMachine, employeeId);
    } catch (Exception exc) {
      throw new RuntimeException(exc);
    }
  }
}
