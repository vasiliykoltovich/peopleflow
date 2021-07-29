package com.peopleflow.consumerservice.service;

import com.peopleflow.consumerservice.configuration.StateMachineProcessor;
import common.EmployeeDTO;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class Consumer {

  private StateMachineProcessor stateMachineProcessor;

  @KafkaListener(topics = "employee-topic", groupId = "consumer")
  public void consume(EmployeeDTO employeeDTO) throws IOException {
    log.info(String.format("#### -> Consumed message -> %s", employeeDTO));
    stateMachineProcessor.processMessage(employeeDTO);

  }
}
