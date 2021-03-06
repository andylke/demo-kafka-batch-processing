package io.github.andylke.demo.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration(proxyBeanMethods = false)
class FooTopicConfiguration {

  @Value("${spring.kafka.listener.concurrency}")
  private int partitionSize;

  @Value("${batch-processing.topic}")
  private String topic;

  @Bean
  public NewTopic fooTopic() {
    return TopicBuilder
        .name(topic)
        .partitions(partitionSize)
        .build();
  }
}
