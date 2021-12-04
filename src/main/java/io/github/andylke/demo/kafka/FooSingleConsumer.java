package io.github.andylke.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
    prefix = "spring.kafka.listener",
    name = "type",
    havingValue = "SINGLE",
    matchIfMissing = true)
public class FooSingleConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(FooSingleConsumer.class);

  @Autowired private FooProcessor processor;

  @KafkaListener(topics = "${batch-processing.topic}")
  public void receive(String record) throws InterruptedException {
    LOGGER.info("Received [{}]", record);

    processor.process(record);
  }
}
