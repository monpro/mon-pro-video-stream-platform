package com.monpro.service;

import com.monpro.dao.UserFollowingDao;
import com.monpro.domain.FollowingGroup;
import com.monpro.domain.User;
import com.monpro.domain.UserFollowing;
import com.monpro.domain.UserInfo;
import com.monpro.domain.constant.UserConstant;
import com.monpro.domain.exception.ConditionException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

  public List<FollowingGroup> getUserFollowings(final Long userId) {
    // 1. get the userFollowingList
    final List<UserFollowing> userFollowingList = userFollowingDao.getUserFollowings(userId);
    final Set<Long> followingIdSet = userFollowingList.stream().map(UserFollowing::getFollowingId).collect(Collectors.toSet());
    List<UserInfo> userInfoList = new ArrayList<>();

    // 2. Get UserInfoList based on the userFollowingIds
    if (followingIdSet.size() > 0) {
      userInfoList = userService.getUserInfoByUserIds(followingIdSet);
    }
    for (final UserFollowing userFollowing : userFollowingList) {
      for(final UserInfo userInfo : userInfoList) {
        if (userFollowing.getFollowingId().equals(userInfo.getUserId())) {
          userFollowing.setUserInfo(userInfo);
        }
      }
    }

    // 3. Group the userFollowings into followingGroups
    final List<FollowingGroup> groupList = followingGroupService.getByUserId(userId);
    final FollowingGroup allGroup = FollowingGroup
        .builder()
        .name(UserConstant.USER_FOLLOWING_GROUP_ALL_NAME)
        .userInfoList(userInfoList)
        .build();
    final List<FollowingGroup> result = new ArrayList<>();
    result.add(allGroup);
    for (final FollowingGroup followingGroup : groupList) {
      final List<UserInfo> userInfos = new ArrayList<>();
      for(final UserFollowing userFollowing : userFollowingList) {
        if (userFollowing.getGroupId().equals(followingGroup.getId())) {
          userInfos.add(userFollowing.getUserInfo());
        }
      }
      followingGroup.setUserInfoList(userInfos);
      result.add(followingGroup);
    }
    return result;
  }

  public List<UserFollowing> getUserFans(final Long userId) {
    final List<UserFollowing> fansList = userFollowingDao.getUserFans(userId);
    final Set<Long> fansIdList = fansList.stream().map(UserFollowing::getUserId).collect(Collectors.toSet());

    List<UserInfo> userInfoList = new ArrayList<>();
    // 2. Get UserInfoList based on the userFollowingIds
    if (fansIdList.size() > 0) {
      userInfoList = userService.getUserInfoByUserIds(fansIdList);
    }

    final List<UserFollowing> followingList = userFollowingDao.getUserFollowings(userId);
    for (final UserFollowing fan: fansList) {
      for (final UserInfo userInfo: userInfoList) {
        if (fan.getUserId().equals(userInfo.getUserId())) {
          fan.setUserInfo(userInfo);
          userInfo.setFollowed(false);
        }
      }

      for (final UserFollowing userFollowing : followingList) {
        if (userFollowing.getUserId().equals(fan.getUserId())) {
          // it means user <- follow -> fan
          fan.getUserInfo().setFollowed(true);
        }
      }
    }

    return fansList;

  }

  public Long addUserFollowingGroup(final FollowingGroup followingGroup) {
    followingGroup.setCreateTime(new Date());
    followingGroup.setType(UserConstant.CUSTOMISE_USER_FOLLOWING_GROUP_TYPE);
    followingGroupService.addFollowingGroup(followingGroup);
    return followingGroup.getId();
  }

  public List<FollowingGroup> getUserFollowingGroups(final Long userId) {
    return followingGroupService.getUserFollowingGroups(userId);
  }

  public List<UserInfo> checkFollowingStatus(List<UserInfo> userInfoList, Long currentUserId) {
    final List<UserFollowing> userFollowingList = userFollowingDao.getUserFollowings(currentUserId);
    for (final UserInfo userInfo: userInfoList) {
      userInfo.setFollowed(false);
      for (final UserFollowing userFollowing : userFollowingList) {
        if (userFollowing.getFollowingId().equals(userInfo.getUserId())) {
          userInfo.setFollowed(true);
        }
      }
    }
    return userInfoList;
  }
}
