package com.peopleflow.producerapi.config;

import common.EmployeeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final String topicName;

  public KafkaProducer(
      final KafkaTemplate<String, Object> template,
      @Value("${producer.topic-name}") final String topicName
      ) {
    this.kafkaTemplate = template;
    this.topicName = topicName;
  }

  public void sendMessage(EmployeeDTO emp) {
    log.info(String.format("#### -> Producing message to topic {} -> %s", topicName, emp));
    this.kafkaTemplate.send(topicName, emp);
  }

}
