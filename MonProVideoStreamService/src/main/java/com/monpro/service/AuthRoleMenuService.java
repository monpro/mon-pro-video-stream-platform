package com.monpro.service;

import com.monpro.dao.AuthRoleMenuDao;
import com.monpro.domain.auth.AuthRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthRoleMenuService {

  @Autowired
  private AuthRoleMenuDao authRoleMenuDao;

  public List<AuthRoleMenu> getAuthRoleMenusByRoleIds(List<Long> roleIdList) {
    return authRoleMenuDao.getAuthRoleMenusByRoleIds(roleIdList);
  }
}
