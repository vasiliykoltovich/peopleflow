package response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Value
@Builder
@JsonDeserialize(builder = ResponseWrapper.ResponseWrapperBuilder.class)
public class ResponseWrapper<T> {
  private T data;

  public static <T> ResponseWrapper<T> data(T data) {
    return ResponseWrapper.<T>builder().data(data).build();
  }

  @SuppressWarnings("unused")
  @JsonPOJOBuilder(withPrefix = "")
  public static class ResponseWrapperBuilder<T> {

  }
}
