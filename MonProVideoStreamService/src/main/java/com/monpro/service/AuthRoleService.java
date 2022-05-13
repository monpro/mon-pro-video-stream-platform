package com.monpro.service;

import com.monpro.domain.auth.AuthRoleElementOperation;
import com.monpro.domain.auth.AuthRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthRoleService {

  @Autowired
  private AuthRoleElementOperationService authRoleElementOperationService;

  @Autowired
  private AuthRoleMenuService authRoleMenuService;

  public List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(List<Long> roleIdList) {
    return authRoleElementOperationService.getRoleElementOperationsByRoleIds(roleIdList);
  }

  public List<AuthRoleMenu> getAuthRoleMenusByRoleIds(List<Long> roleIdList) {
    return authRoleMenuService.getAuthRoleMenusByRoleIds(roleIdList);

  }
}
