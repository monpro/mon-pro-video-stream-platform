package com.monpro.dao;

import com.alibaba.fastjson.JSONObject;
import com.monpro.domain.User;
import com.monpro.domain.UserFollowing;
import com.monpro.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface UserDao {
  User getUserByPhone(final String phone);

  Integer addUser(final User user);

  Integer addUserInfo(final UserInfo userInfo);

  User getUserById(final Long id);

  UserInfo getUserInfoByUserId(final Long userId);

  Integer updateUsers(final User user);

  List<UserInfo> getUserInfoByUserIds(Set<Long> userIds);

  Integer updateUserInfo(UserInfo userInfo);

  Integer pageCountUserInfos(Map<String, Object> params);

  List<UserInfo> pageListUserInfos(Map<String, Object> params);
}
