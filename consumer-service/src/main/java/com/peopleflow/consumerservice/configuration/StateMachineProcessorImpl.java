package com.peopleflow.consumerservice.configuration;


import static common.EmployeeConstants.EMPLOYEE;

import com.peopleflow.consumerservice.utils.EventMapper;
import com.peopleflow.consumerservice.utils.StateMachineUtil;
import common.EmployeeDTO;
import common.EmployeeEvent;
import common.EmployeeState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StateMachineProcessorImpl implements StateMachineProcessor {

  private StateMachineStateStorage stateMachineStateStorage;

  public StateMachineProcessorImpl(StateMachineStateStorage stateMachineStateStorage) {
    this.stateMachineStateStorage = stateMachineStateStorage;
  }

  @Override
  public void processMessage(EmployeeDTO employeeDTO) {
    String employeeId = employeeDTO.getEmployeeId();
    StateMachine<EmployeeState, EmployeeEvent> stateMachine = stateMachineStateStorage.getState(employeeId);
    StateMachineUtil.putEmployee(stateMachine, employeeDTO);
    stateMachine.sendEvent(EventMapper.getEventForState(employeeDTO.getState()));
    stateMachineStateStorage.saveState(employeeId, stateMachine);
  }
}
