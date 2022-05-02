package com.monpro.service.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

import static com.monpro.domain.constant.UserMomentConstant.MOMENTS_GROUP;
import static com.monpro.domain.constant.UserMomentConstant.MOMENTS_TOPIC;

@Configuration
@Slf4j
public class RocketMQConfig {

  @Value("${rocketmq.name.server.address}")
  private String nameServerAddress;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Bean("momentsProducer")
  public DefaultMQProducer momentsProducer() throws MQClientException {
    final DefaultMQProducer producer = new DefaultMQProducer(MOMENTS_GROUP);
    producer.setNamesrvAddr(nameServerAddress);
//    producer.start();
    return producer;
  }

  @Bean("momentsConsumer")
  public DefaultMQPushConsumer momentsConsumer() throws MQClientException {
    final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(MOMENTS_GROUP);
    consumer.setNamesrvAddr(nameServerAddress);
    consumer.subscribe(MOMENTS_TOPIC, "*");
    consumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> list, final ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for(final MessageExt msg: list) {
          log.info(msg.toString());
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });
//    consumer.start();
    return consumer;
  }

}
