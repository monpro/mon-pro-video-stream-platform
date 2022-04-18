package com.monpro.service;

import com.monpro.domain.User;
import com.monpro.dao.UserDao;
import com.monpro.domain.UserInfo;
import com.monpro.domain.constant.UserConstant;
import com.monpro.domain.exception.ConditionException;
import com.monpro.service.util.MD5Util;
import com.monpro.service.util.RSAUtil;
import com.monpro.service.util.TokenUtil;
import com.mysql.cj.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

  @Autowired
  private final UserDao userDao;

  public void addUser(final User user) {
    final String phone = user.getPhone();
    if (StringUtils.isNullOrEmpty(phone)) {
      throw new ConditionException("user's phone cannot be empty");
    }
    final User dbUser = this.getUserByPhone(phone);
    if (dbUser != null) {
      throw new ConditionException("user already registered");
    }
    final Date now = new Date();
    final String salt = user.getSalt();
    final String password = user.getPassword();
    final String rawPassword = RSAUtil.decrypt(password);
    final String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
    user.setPassword(md5Password);
    user.setCreateTime(now);
    userDao.addUser(user);
    // create UserInfo
    final UserInfo userInfo = new UserInfo();
    userInfo.setUserId(user.getId());
    userInfo.setNick(UserConstant.DEFAULT_NICK);
    userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
    userInfo.setGender(UserConstant.GENDER_MALE);
    userInfo.setCreateTime(now);
    userDao.addUserInfo(userInfo);
  }

  public User getUserByPhone(final String phone) {
    return userDao.getUserByPhone(phone);
  }

  public String login(final User user) {
    final String phone = user.getPhone();
    if (StringUtils.isNullOrEmpty(phone)) {
      throw new ConditionException("user's phone cannot be empty");
    }
    final User dbUser = this.getUserByPhone(phone);
    if (dbUser == null) {
      throw new ConditionException("user not existed");
    }
    final String password = user.getPassword();
    final String rawPassword = RSAUtil.decrypt(password);
    final String salt = dbUser.getSalt();
    final String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
    if (!md5Password.equals(dbUser.getPassword())) {
      throw new ConditionException("password is wrong");
    }
    return TokenUtil.getToken(dbUser.getId());
  }

  public User getUserData(final Long userId) {
    final UserInfo userInfo = userDao.getUserInfoByUserId(userId);
    final User user = userDao.getUserById(userId);
    user.setUserInfo(userInfo);
    return user;
  }

  public void updateUser(final User user) {
    final Long id = user.getId();
    final User dbUser = userDao.getUserById(id);
    if (dbUser == null) {
      throw new ConditionException("user not existed");
    }
    if (!StringUtils.isNullOrEmpty(user.getPassword())) {
      final String rawPassword = RSAUtil.decrypt(user.getPassword());
      final String md5Password = MD5Util.sign(rawPassword, dbUser.getSalt(), "UTF-8");
      user.setPassword(md5Password);
    }
    user.setUpdateTime(new Date());
    userDao.updateUsers(user);
  }

  public User getUserById(final Long followingId) {
    return userDao.getUserById(followingId);
  }
}
