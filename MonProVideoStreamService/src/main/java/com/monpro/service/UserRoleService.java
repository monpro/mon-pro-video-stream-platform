package com.monpro.service;

import com.monpro.dao.UserRoleDao;
import com.monpro.domain.auth.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

  @Autowired
  private UserRoleDao userRoleDao;

  public List<UserRole> getUserRoleByUserId(final Long userId) {
    return userRoleDao.getUserRoleByUserId(userId);
  }
}
