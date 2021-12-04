package io.github.andylke.demo.kafka;

import java.util.List;

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
    havingValue = "BATCH",
    matchIfMissing = false)
public class FooBatchConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(FooBatchConsumer.class);

  @Autowired private FooProcessor processor;

  @KafkaListener(topics = "${batch-processing.topic}")
  public void receive(List<String> records) throws InterruptedException {
    LOGGER.info("Received [{}] records", records.size());

    records
        .parallelStream()
        .forEach(
            (record) -> {
              try {
                processor.process(record);
              } catch (InterruptedException e) {
                throw new RuntimeException("Failed to process [" + record + "]", e);
              }
            });
  }
}
