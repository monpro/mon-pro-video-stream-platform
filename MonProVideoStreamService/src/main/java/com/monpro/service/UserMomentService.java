package com.monpro.service;

import com.alibaba.fastjson.JSONObject;
import com.monpro.dao.UserMomentDao;
import com.monpro.domain.UserMoment;
import com.monpro.domain.constant.UserMomentConstant;
import com.monpro.service.util.RocketMQUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Slf4j
public class UserMomentService {

  @Autowired
  private UserMomentDao userMomentDao;

  @Autowired
  private ApplicationContext applicationContext;

  public void addUserMoments(UserMoment userMoment) throws Exception {
    userMoment.setCreateTime(new Date());
    userMomentDao.addUserMoments(userMoment);
    final DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("momentsProducer");
    final Message message = new Message(UserMomentConstant.MOMENTS_TOPIC, JSONObject.toJSONString(userMoment).getBytes(StandardCharsets.UTF_8));
    RocketMQUtil.syncSendMsg(producer, message);
  }
}
