package com.monpro.service;

import com.monpro.dao.UserMomentDao;
import com.monpro.domain.UserMoment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class UserMomentService {

  @Autowired
  private UserMomentDao userMomentDao;

  public void addUserMoments(UserMoment userMoment) {
    userMoment.setCreateTime(new Date());
    userMomentDao.addUserMoments(userMoment);
  }
}
