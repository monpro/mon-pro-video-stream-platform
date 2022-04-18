package com.monpro.service;

import com.monpro.dao.UserFollowingDao;
import com.monpro.domain.FollowingGroup;
import com.monpro.domain.User;
import com.monpro.domain.UserFollowing;
import com.monpro.domain.constant.UserConstant;
import com.monpro.domain.exception.ConditionException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@AllArgsConstructor
public class UserFollowingService {

  @Autowired
  private UserFollowingDao userFollowingDao;

  @Autowired
  private FollowingGroupService followingGroupService;

  @Autowired
  private UserService userService;

  @Transactional
  public void addUserFollowing(final UserFollowing userFollowing) {
    final Long groupId = userFollowing.getGroupId();
    if (groupId == null) {
      final FollowingGroup followingGroup = followingGroupService.getByType(UserConstant.DEFAULT_USER_FOLLOWING_GROUP_TYPE);
      userFollowing.setGroupId(followingGroup.getId());
    } else {
      final FollowingGroup followingGroup = followingGroupService.getById(groupId);
      if (followingGroup == null) {
        throw new ConditionException("following group is not existed");
      }
    }

    final Long followingId = userFollowing.getFollowingId();
    final User user = userService.getUserById(followingId);
    if (user == null) {
      throw new ConditionException("user following is not existed");
    }

    userFollowingDao.deleteUserFollowing(userFollowing.getUserId(), followingId);
    userFollowing.setCreateTime(new Date());
    userFollowingDao.addUserFollowing(userFollowing);
  }
}
