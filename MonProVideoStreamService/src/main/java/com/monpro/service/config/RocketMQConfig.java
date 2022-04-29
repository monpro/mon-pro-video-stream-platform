package com.monpro.service.config;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import static com.monpro.domain.constant.UserMomentConstant.MOMENTS_GROUP;
import static com.monpro.domain.constant.UserMomentConstant.MOMENTS_TOPIC;

@Configuration
public class RocketMQConfig {

  @Value("${rocketmq.name.server.address}")
  private String nameServerAddress;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Bean("momentsProducer")
  public DefaultMQProducer momentsProducer() throws MQClientException {
    final DefaultMQProducer producer = new DefaultMQProducer(MOMENTS_GROUP);
    producer.setNamesrvAddr(nameServerAddress);
    producer.start();
    return producer;
  }

  @Bean("momentsConsumer")
  public DefaultMQPushConsumer momentsConsumer() throws MQClientException {
    final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(MOMENTS_GROUP);
    consumer.setNamesrvAddr(nameServerAddress);
    consumer.subscribe(MOMENTS_TOPIC, "*");
    return consumer;
  }

}
