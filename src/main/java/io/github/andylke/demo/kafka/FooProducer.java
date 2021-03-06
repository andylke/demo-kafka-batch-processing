package io.github.andylke.demo.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import io.github.andylke.demo.DemoApplication;

@Component
public class FooProducer {

  private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

  @Autowired private KafkaTemplate<String, String> kafkaTemplate;

  @Value("${batch-processing.records}")
  private int records;

  @Value("${batch-processing.topic}")
  private String topic;

  @EventListener(ApplicationReadyEvent.class)
  public void send() {
    final List<CompletableFuture<?>> futures = new ArrayList<CompletableFuture<?>>();

    final long startTime = System.nanoTime();
    for (int index = 0; index < records; index++) {
      futures.add(kafkaTemplate.send(topic, "foo" + index).completable());
    }
    try {
      CompletableFuture.allOf(futures.toArray(size -> new CompletableFuture<?>[size])).join();
    } catch (CompletionException e) {
      LOGGER.error("Failed sending messages", e);
      throw e;
    }

    LOGGER.info(
        "Sent [{}] records to [{}], elapsed [{}]",
        records,
        topic,
        Duration.ofNanos(startTime - System.nanoTime()));
  }
}
