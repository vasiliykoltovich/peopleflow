package common;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;

@Value
@Data
@ToString
@Builder(toBuilder = true)
@JsonDeserialize(builder = EmployeeDTO.EmployeeDTOBuilder.class)
@RequiredArgsConstructor
public class EmployeeDTO implements Serializable {
  private Integer age;
  private String firstName;
  private String lastName;
  private String employeeId;
  private EmployeeState state;


  @JsonPOJOBuilder(withPrefix = "")
  public static class EmployeeDTOBuilder {

  }


}
