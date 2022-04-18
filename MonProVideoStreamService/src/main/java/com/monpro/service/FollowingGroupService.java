package com.monpro.service;

import com.monpro.dao.FollowingGroupDao;
import com.monpro.domain.FollowingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowingGroupService {

  @Autowired
  private FollowingGroupDao followingGroupDao;

  public FollowingGroup getByType(final String type) {
    return followingGroupDao.getByType(type);
  }

  public FollowingGroup getById(final String id) {
    return followingGroupDao.getById(id);
  }
}
