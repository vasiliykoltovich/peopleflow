package com.peopleflow.consumerservice.service;

import com.peopleflow.consumerservice.data.Employee;
import com.peopleflow.consumerservice.data.EmployeeRepository;
import com.peopleflow.consumerservice.exceptions.EmployeeServiceException;
import com.peopleflow.consumerservice.utils.EmployeeMapper;
import common.EmployeeDTO;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

  private EmployeeRepository employeeRepository;
  private EmployeeMapper employeeMapper;

  @Override
  public EmployeeDTO get(String employeeId) {
    try {
      var employee = employeeRepository.findByEmployeeId(employeeId);
      return employee.isPresent() ? employeeMapper.toEmployeeDTO(employee.get()): null;
    } catch (Exception exc) {
      log.error(exc.getMessage(), exc);
      throw new EmployeeServiceException(exc);
    }
  }

  @Override
  @Transactional
  public EmployeeDTO init(EmployeeDTO employeeDTO) {
    try {
      Employee employee = employeeMapper.toEmployee(employeeDTO);
      employee = employeeRepository.save(employee);
      return employeeMapper.toEmployeeDTO(employee);
    } catch (Exception exc) {
      log.error(exc.getMessage(), exc);
      throw new EmployeeServiceException(exc);
    }
  }

  @Override
  @Transactional
  public EmployeeDTO update(EmployeeDTO employeeDTO) {
    try {
      Optional<Employee> employee = employeeRepository.findByEmployeeId(employeeDTO.getEmployeeId());
      if (employee.isEmpty()) {
        throw new EmployeeServiceException("Not found employee with employeeId: "+ employeeDTO.getEmployeeId());
      }
      Employee entity = employee.get();
      entity.setState(employeeDTO.getState());
      entity = employeeRepository.save(entity);
      return employeeMapper.toEmployeeDTO(entity);
    } catch (RuntimeException exc) {
      log.error(exc.getMessage(), exc);
      throw new EmployeeServiceException(exc);
    }
  }
}
