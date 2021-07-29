package com.peopleflow.producerapi.controller;

import com.peopleflow.producerapi.data.NewEmployee;
import common.EmployeeDTO;
import common.EmployeeState;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import response.ResponseWrapper;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ManagerController {

  private final EmployeeService employeeService;

  @SneakyThrows
  @RequestMapping(value = "/employee",
      method = RequestMethod.POST,
      produces = {MediaType.APPLICATION_JSON_VALUE,
          MediaType.APPLICATION_XML_VALUE})
  @ResponseBody
  @ResponseStatus(code = HttpStatus.ACCEPTED)
  public ResponseWrapper<EmployeeDTO> addEmployee(@RequestBody NewEmployee newEmployee) {
    EmployeeDTO emp = EmployeeDTO.builder()
        .age(newEmployee.getAge())
        .firstName(newEmployee.getFirstName())
        .lastName(newEmployee.getLastName())
        .build();
    EmployeeDTO employeeDTO = employeeService.addEmployee(emp);
    return ResponseWrapper.<EmployeeDTO>builder().data(employeeDTO).build();
  }

  @SneakyThrows
  @RequestMapping(value = "/changeStatus",
      method = RequestMethod.POST)
  @ResponseBody
  @ResponseStatus(code = HttpStatus.ACCEPTED)
  public ResponseWrapper<Boolean> changeStatus(@RequestParam("employeeId")String employeeId, @RequestParam("status")String status) {
    EmployeeState state = EmployeeState.valueOf(status.trim().toUpperCase());
    boolean result = employeeService.changeStatus(employeeId, state);
    return ResponseWrapper.<Boolean>builder().data(result).build();
  }

}
