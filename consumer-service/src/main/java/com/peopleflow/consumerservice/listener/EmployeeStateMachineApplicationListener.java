package com.peopleflow.consumerservice.listener;

import common.EmployeeEvent;
import common.EmployeeState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmployeeStateMachineApplicationListener implements StateMachineListener<EmployeeState, EmployeeEvent> {

  @Override
  public void stateChanged(State<EmployeeState, EmployeeEvent> state, State<EmployeeState, EmployeeEvent> state1) {
    if (state != null && state1 != null) {
      if (state.getId() != null) {
        log.info("From status {}  to new status {} ", state.getId(), state1.getId());
      }
    } else {
      log.info("State:", state != null ? state.getId() : null);
      log.info("State1:", state1 != null ? state1.getId() : null);
    }
  }

  @Override
  public void stateEntered(State<EmployeeState, EmployeeEvent> state) {
    log.info("State entered: {}", state != null ? state.getId() : "");
  }

  @Override
  public void stateExited(State<EmployeeState, EmployeeEvent> state) {
    log.info("State exited: {}", state != null ? state.getId() : "");
  }

  @Override
  public void eventNotAccepted(Message<EmployeeEvent> message) {
    log.info("Event is not accepted: {}", message.getPayload());
  }

  @Override
  public void transition(Transition<EmployeeState, EmployeeEvent> transition) {
    log.info("Processing transition: {}",
        transition != null ? transition.getKind() + "source:" + transition.getSource() + "target:" + transition
            .getTarget() : "");
  }

  @Override
  public void transitionStarted(Transition<EmployeeState, EmployeeEvent> transition) {
    log.info("Transition started: {}", transition != null ? transition.getKind()  : "");
    log.info("Transition started source: {}",transition != null ? transition.getSource() : "");
    log.info("Transition started target: {}",transition != null ? transition.getTarget() : "");

  }

  @Override
  public void transitionEnded(Transition<EmployeeState, EmployeeEvent> transition) {
    log.info("Transition ended: {}",
        transition != null ? transition.getKind() : "");
    log.info("Transition ended source: {}",transition != null ? transition.getSource() : "");
    log.info("Transition ended target: {}",transition != null ? transition.getTarget() : "");

  }

  @Override
  public void stateMachineStarted(StateMachine<EmployeeState, EmployeeEvent> stateMachine) {
    log.info("StateMachine started !!!: {}", stateMachine!=null ? stateMachine.getState().getId() : "");
  }

  @Override
  public void stateMachineStopped(StateMachine<EmployeeState, EmployeeEvent> stateMachine) {
    log.info("StateMachine stopped !!!: {}", stateMachine!=null ? stateMachine.getState().getId() : "");
  }

  @Override
  public void stateMachineError(StateMachine<EmployeeState, EmployeeEvent> stateMachine, Exception e) {
    log.info("StateMachine exception:{}", e.getMessage());
  }

  @Override
  public void extendedStateChanged(Object o, Object o1) {
    log.info("Extended State Changed:from {} to {}", o, o1);
  }

  @Override
  public void stateContext(StateContext<EmployeeState, EmployeeEvent> stateContext) {

  }
}
