package com.monpro.service;

import com.monpro.dao.AuthRoleElementOperationDao;
import com.monpro.domain.auth.AuthRoleElementOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthRoleElementOperationService {

  @Autowired
  private AuthRoleElementOperationDao authRoleElementOperationDao;

  public List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(final List<Long> roleIdList) {
    return authRoleElementOperationDao.getRoleElementOperationsByRoleIds(roleIdList);

  }
}
