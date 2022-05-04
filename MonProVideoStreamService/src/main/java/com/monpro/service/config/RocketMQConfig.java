package com.monpro.service.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.monpro.domain.UserFollowing;
import com.monpro.domain.UserMoment;
import com.monpro.service.UserFollowingService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

import java.util.ArrayList;
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

  @Autowired
  private UserFollowingService userFollowingService;

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
    consumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> list, final ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        final MessageExt messageExt = list.get(0);
        if (messageExt == null) {
          return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        final String bodyString = new String(messageExt.getBody());
        final UserMoment userMoment = JSONObject.toJavaObject(JSONObject.parseObject(bodyString), UserMoment.class);
        final Long userId = userMoment.getUserId();
        final List<UserFollowing> userFans = userFollowingService.getUserFans(userId);
        userFans.forEach(userFan -> {
          final String key = "subscribe-" + userFan.getUserId();
          final String subscribeListString = redisTemplate.opsForValue().get(key);
          final List<UserMoment> subscribeList;
          if (StringUtil.isNullOrEmpty(subscribeListString)) {
            subscribeList = new ArrayList<>();
          } else {
            subscribeList = JSONArray.parseArray(subscribeListString, UserMoment.class);
          }
          subscribeList.add(userMoment);
          redisTemplate.opsForValue().set(key, JSONObject.toJSONString(subscribeList));
        });
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });
    consumer.start();
    return consumer;
  }

}
