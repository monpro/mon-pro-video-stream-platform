package com.monpro.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
  User getUserByPhone(final String phone);

  Integer addUser(final User user);

  Integer addUserInfo(final UserInfo userInfo);
}
