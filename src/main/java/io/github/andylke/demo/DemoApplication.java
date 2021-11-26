package io.github.andylke.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import io.github.andylke.demo.kafka.FooProducer;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Autowired private FooProducer producer;

  @EventListener(ApplicationReadyEvent.class)
  void ready() throws Exception {
    producer.send();
  }
}
