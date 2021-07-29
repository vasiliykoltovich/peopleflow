package com.peopleflow.producerapi.controller;


import com.peopleflow.producerapi.config.KafkaProducer;
import com.peopleflow.producerapi.exceptions.EmployeeStatusUpdateException;
import com.peopleflow.producerapi.exceptions.NewEmployeeException;
import common.EmployeeDTO;
import common.EmployeeState;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

  private KafkaProducer producer;

  @Override
  public EmployeeDTO addEmployee(EmployeeDTO emp) {
    String empId = UUID.randomUUID().toString();
    EmployeeDTO populatedEmployeeDTO = emp.toBuilder().employeeId(empId).build();
    sendEvent(populatedEmployeeDTO);
    return populatedEmployeeDTO;
  }

  @Override
  public boolean changeStatus(String employeeId, EmployeeState status) {
    return updateState(employeeId, status);
  }

  private void sendEvent(EmployeeDTO employeeDTO) {
    try {
      producer.sendMessage(employeeDTO);
    } catch (Exception exc) {
      log.error(exc.getMessage(), exc);
      throw new NewEmployeeException(exc);
    }
  }

  private boolean updateState(String employeeId, EmployeeState state) {
    try {
      EmployeeDTO tempEmployeeDTO = EmployeeDTO.builder().employeeId(employeeId).state(state).build();
      sendEvent(tempEmployeeDTO);
      return true;
    } catch (Exception exc) {
      log.error(exc.getMessage(), exc);
      throw new EmployeeStatusUpdateException(employeeId, state);
    }
  }
}
