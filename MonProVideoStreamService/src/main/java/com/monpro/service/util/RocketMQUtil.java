package com.monpro.service.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

@Slf4j
public class RocketMQUtil {

  public static void syncSendMsg(final DefaultMQProducer producer, final Message message) throws Exception {
    final SendResult sendResult = producer.send(message);
    log.info(sendResult.toString());
  }

  public static void asyncSendMsg(final DefaultMQProducer producer, final Message message) throws Exception {
    producer.send(message, new SendCallback() {
      @Override
      public void onSuccess(SendResult sendResult) {
        log.info("async send msg success, msg id: " + sendResult.getMsgId());
      }
      @Override
      public void onException(Throwable throwable) {
        throwable.printStackTrace();
      }
    });
  }
}
