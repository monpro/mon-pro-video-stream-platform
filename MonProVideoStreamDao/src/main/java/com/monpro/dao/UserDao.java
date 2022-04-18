package com.monpro.dao;

import com.monpro.domain.User;
import com.monpro.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
  User getUserByPhone(final String phone);

  Integer addUser(final User user);

  Integer addUserInfo(final UserInfo userInfo);

  User getUserById(final Long id);

  UserInfo getUserInfoByUserId(final Long userId);

  Integer updateUsers(final User user);
}
