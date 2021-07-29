package com.peopleflow.consumerservice.data;

import common.EmployeeState;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employees")
@Setter
@Getter
public class Employee implements Serializable {
  @Id
  private ObjectId _id;
  private Integer age;
  private String firstName;
  private String lastName;
  @Indexed(unique = true)
  private String employeeId;
  private EmployeeState state;

}
