package com.peopleflow.consumerservice.utils;

import common.EmployeeEvent;
import common.EmployeeState;
import java.util.Objects;

public class EventMapper {

  public static EmployeeEvent getEventForState(EmployeeState state) {
    state = Objects.requireNonNullElse(state, EmployeeState.INITIAL);
    switch (state) {
      case IN_CHECK:
        return EmployeeEvent.CHECK;
      case APPROVED:
        return EmployeeEvent.APPROVE;
      case ACTIVE:
        return EmployeeEvent.ACTIVATE;
      default:
        return EmployeeEvent.INIT;
    }
  }
}
