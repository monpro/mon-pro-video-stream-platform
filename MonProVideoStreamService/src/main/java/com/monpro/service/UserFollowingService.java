package com.monpro.service;

import com.monpro.dao.UserFollowingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFollowingService {

  @Autowired
  private UserFollowingDao userFollowingDao;
}
