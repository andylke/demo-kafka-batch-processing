package io.github.andylke.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FooProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(FooProcessor.class);

  public void process(String record) throws InterruptedException {
    Thread.sleep(200);

    LOGGER.info("Processed record [{}]", record);
  }
}
