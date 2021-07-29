package com.peopleflow.consumerservice.statemachine;

import static common.EmployeeConstants.EMPLOYEE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.peopleflow.consumerservice.actions.ChangeAction;
import com.peopleflow.consumerservice.actions.HideGuard;
import com.peopleflow.consumerservice.actions.InitAction;
import com.peopleflow.consumerservice.data.Employee;
import com.peopleflow.consumerservice.service.EmployeeService;
import com.peopleflow.consumerservice.utils.EventMapper;
import com.peopleflow.consumerservice.utils.StateMachineUtil;
import common.EmployeeDTO;
import common.EmployeeEvent;
import common.EmployeeState;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ActionsTest {

  @MockBean
  private EmployeeService employeeService;
  @Mock
  private StateMachine<EmployeeState, EmployeeEvent> stateMachine;
  @Mock
  private State<EmployeeState, EmployeeEvent> state;
  @Mock
  private StateContext<EmployeeState, EmployeeEvent> stateContext;
  @SpyBean
  private ChangeAction changeAction;


  @Test
  public void testInitEmployeeActionExpectSuccessfulResult() {
    try (MockedStatic<StateMachineUtil> util = Mockito.mockStatic(StateMachineUtil.class)) {
      var employeeDTO = EmployeeDTO.builder().build();
      when(stateContext.getStateMachine()).thenReturn(stateMachine);
      util.when(() -> StateMachineUtil.getEmployee(stateMachine)).thenReturn(employeeDTO);
      new InitAction(employeeService).execute(stateContext);
      ArgumentCaptor<EmployeeDTO> employeeDTOCapture = ArgumentCaptor.forClass(EmployeeDTO.class);
      verify(employeeService, times(1)).init((employeeDTOCapture.capture()));
      assertThat(employeeDTOCapture.getValue().getState()).isEqualTo(EmployeeState.ADDED);
    }
  }

  @Test
  public void testUpdateEmployeeAction() {
    try (MockedStatic<StateMachineUtil> util = Mockito.mockStatic(StateMachineUtil.class)) {
      var employeeDTO = EmployeeDTO.builder().build();
      when(stateContext.getStateMachine()).thenReturn(stateMachine);
      when(state.getId()).thenReturn(EmployeeState.IN_CHECK);
      when(stateContext.getTarget()).thenReturn(state);
      util.when(() -> StateMachineUtil.getEmployee(stateMachine)).thenReturn(employeeDTO);
      doReturn(EmployeeState.IN_CHECK).when(changeAction).getTargetState(same(stateContext));

      changeAction.execute(stateContext);

      verify(employeeService, times(1)).update(any(EmployeeDTO.class));
    }
  }


  @Test
  public void testEmployeeGuard() {
    try (MockedStatic<StateMachineUtil> util = Mockito.mockStatic(StateMachineUtil.class)) {
      var employeeId = UUID.randomUUID().toString();
      var employeeDTO = EmployeeDTO.builder().employeeId(employeeId).build();
      when(stateContext.getStateMachine()).thenReturn(stateMachine);
      util.when(() -> StateMachineUtil.getEmployee(stateMachine)).thenReturn(employeeDTO);
      when(employeeService.get(eq(employeeId))).thenReturn(employeeDTO);

      boolean result = new HideGuard(employeeService).evaluate(stateContext);
      assertTrue(result);
    }
  }

}


