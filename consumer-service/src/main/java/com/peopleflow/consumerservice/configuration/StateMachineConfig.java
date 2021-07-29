package com.peopleflow.consumerservice.configuration;

import com.peopleflow.consumerservice.actions.ChangeAction;
import com.peopleflow.consumerservice.actions.ErrorAction;
import com.peopleflow.consumerservice.actions.HideGuard;
import com.peopleflow.consumerservice.actions.InitAction;
import com.peopleflow.consumerservice.listener.EmployeeStateMachineApplicationListener;
import com.peopleflow.consumerservice.persister.EmployeeStateMachinePersister;
import com.peopleflow.consumerservice.service.EmployeeService;
import common.EmployeeEvent;
import common.EmployeeState;
import java.util.EnumSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

@Slf4j
@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<EmployeeState, EmployeeEvent> {

  @Autowired
  private EmployeeService employeeService;

  @Override
  public void configure(StateMachineConfigurationConfigurer<EmployeeState, EmployeeEvent> config) throws Exception {
    config.withConfiguration()
        .autoStartup(true)
        .machineId("employeeStateMachine")
        .listener(new EmployeeStateMachineApplicationListener());
  }

  @Override
  public void configure(StateMachineStateConfigurer<EmployeeState, EmployeeEvent> states) throws Exception {
    states.withStates()
        .initial(EmployeeState.INITIAL)
        .end(EmployeeState.ACTIVE)
        .states(EnumSet.allOf(EmployeeState.class));
  }

  @Override
  public void configure(StateMachineTransitionConfigurer<EmployeeState, EmployeeEvent> transitions) throws Exception {
    transitions.withExternal()
        .source(EmployeeState.INITIAL)
        .target(EmployeeState.ADDED)
        .event(EmployeeEvent.INIT)
        .action(initAction(), errorAction())

        .and()
        .withExternal()
        .source(EmployeeState.ADDED)
        .target(EmployeeState.IN_CHECK)
        .event(EmployeeEvent.CHECK)
        .action(changeAction(), errorAction())

        .and()
        .withExternal()
        .source(EmployeeState.IN_CHECK)
        .target(EmployeeState.APPROVED)
        .event(EmployeeEvent.APPROVE)
        .action(changeAction(), errorAction())

        .and()
        .withExternal()
        .source(EmployeeState.APPROVED)
        .target(EmployeeState.ACTIVE)
        .event(EmployeeEvent.ACTIVATE)
        .guard(hideGuard())
        .action(changeAction(), errorAction());
  }

  @Bean
  public Action<EmployeeState, EmployeeEvent> changeAction() {
    return new ChangeAction(employeeService);
  }

  @Bean
  public Action<EmployeeState, EmployeeEvent> initAction() {
    return new InitAction(employeeService);
  }

  @Bean
  public Action<EmployeeState, EmployeeEvent> errorAction() {
    return new ErrorAction();
  }

  @Bean
  public Guard<EmployeeState, EmployeeEvent> hideGuard() {
    return new HideGuard(employeeService);
  }

  @Bean
  public StateMachinePersister<EmployeeState, EmployeeEvent, String> persister() {
    return new DefaultStateMachinePersister<>(new EmployeeStateMachinePersister());
  }


}
