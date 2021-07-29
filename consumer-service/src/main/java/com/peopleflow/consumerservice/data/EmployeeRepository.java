package com.peopleflow.consumerservice.data;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, Long> {

  Optional<Employee> findByEmployeeId(String employeeId);

}
