package com.monpro.service;

import com.monpro.dao.FollowingGroupDao;
import com.monpro.domain.FollowingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowingGroupService {

  @Autowired
  private FollowingGroupDao followingGroupDao;

  public FollowingGroup getByType(final String type) {
    return followingGroupDao.getByType(type);
  }

  public FollowingGroup getById(final Long id) {
    return followingGroupDao.getById(id);
  }

  public List<FollowingGroup> getByUserId(final Long userId) {
    return followingGroupDao.getByUserId(userId);
  }

  public void addFollowingGroup(FollowingGroup followingGroup) {
    followingGroupDao.addFollowingGroup(followingGroup);
  }

  public List<FollowingGroup> getUserFollowingGroups(Long userId) {
    return followingGroupDao.getUserFollowingGroups(userId);
  }
}
