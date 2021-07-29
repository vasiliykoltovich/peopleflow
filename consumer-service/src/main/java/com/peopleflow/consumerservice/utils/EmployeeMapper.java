package com.peopleflow.consumerservice.utils;

import com.peopleflow.consumerservice.data.Employee;
import common.EmployeeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

  Employee toEmployee(EmployeeDTO employeeDTO);
  EmployeeDTO toEmployeeDTO(Employee employee);
}
