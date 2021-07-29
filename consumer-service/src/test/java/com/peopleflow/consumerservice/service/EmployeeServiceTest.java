package com.peopleflow.consumerservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.peopleflow.consumerservice.data.Employee;
import com.peopleflow.consumerservice.data.EmployeeRepository;
import com.peopleflow.consumerservice.exceptions.EmployeeServiceException;
import com.peopleflow.consumerservice.utils.EmployeeMapper;
import com.peopleflow.consumerservice.utils.EmployeeMapperImpl;
import common.EmployeeDTO;
import common.EmployeeState;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

  @MockBean
  private EmployeeRepository employeeRepository;

  private EmployeeServiceImpl employeeService;

  private EmployeeMapper mapper = new EmployeeMapperImpl();

  @BeforeEach
  public void init() {
    employeeService = new EmployeeServiceImpl(employeeRepository, mapper);
  }

  @Test
  public void saveEmployeeExpectSuccesfullResult() {
    String id = UUID.randomUUID().toString();
    var employeeDTO = EmployeeDTO.builder()
        .employeeId(id)
        .age(100)
        .firstName("ABC")
        .lastName("BCA")
        .build();
    var employee = mapper.toEmployee(employeeDTO);
    employee.setState(EmployeeState.ADDED);
    when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
    var savedEmployee = employeeService.init(employeeDTO);
    assertEquals(id, savedEmployee.getEmployeeId());
    assertEquals(EmployeeState.ADDED, savedEmployee.getState());
  }

  @Test
  public void saveEmployeeExpectExceptionWhileSaving() {
    when(employeeRepository.save(any(Employee.class))).thenThrow(RuntimeException.class);
    assertThrows(EmployeeServiceException.class, () -> employeeService.init(EmployeeDTO.builder().build()));

  }

  @Test
  public void updateEmployeeExpectSuccessfullResult() {
    String id = UUID.randomUUID().toString();
    var employeeDTO = EmployeeDTO.builder()
        .employeeId(id)
        .age(100)
        .firstName("ABC")
        .lastName("BCA")
        .state(EmployeeState.IN_CHECK)
        .build();
    var employee = mapper.toEmployee(employeeDTO);
    ArgumentCaptor<Employee> employeeCapture = ArgumentCaptor.forClass(Employee.class);
    when(employeeRepository.findByEmployeeId(eq(id))).thenReturn(Optional.of(employee));
    when(employeeRepository.save(any(Employee.class)))
        .thenAnswer((Answer<Employee>) invocationOnMock -> invocationOnMock.getArgument(0));
    EmployeeDTO updatedEmployee = employeeService.update(employeeDTO);
    verify(employeeRepository, times(1)).save(employeeCapture.capture());
    assertEquals(id, updatedEmployee.getEmployeeId());
    assertThat(employeeCapture.getValue().getEmployeeId()).isEqualTo(id);
    assertThat(employeeCapture.getValue().getState()).isEqualTo(EmployeeState.IN_CHECK);
    assertEquals(EmployeeState.IN_CHECK, updatedEmployee.getState());
  }

  @Test
  public void updateEmployeeExpectErrorEmployeeNotExist() {
    String id = UUID.randomUUID().toString();
    int newAge = 21;
    var employeeDTO = EmployeeDTO.builder()
        .firstName("ABC")
        .lastName("BAC")
        .age(100)
        .employeeId(id)
        .build();
    when(employeeRepository.findByEmployeeId(eq(id))).thenReturn(Optional.empty());

    var employeeException = assertThrows(EmployeeServiceException.class, () -> {
      employeeService.update(employeeDTO);
    });

    assertThat(employeeException.getMessage())
        .as("Unexpected status: %s", employeeException)
        .contains("Not found employee with employeeId: " + id);

  }

}
