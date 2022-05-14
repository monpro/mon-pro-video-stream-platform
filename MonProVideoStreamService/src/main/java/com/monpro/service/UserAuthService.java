package com.monpro.service;

import com.monpro.domain.auth.AuthRoleElementOperation;
import com.monpro.domain.auth.AuthRoleMenu;
import com.monpro.domain.auth.UserAuthorities;
import com.monpro.domain.auth.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserAuthService {

  @Autowired
  private UserRoleService userRoleService;

  @Autowired
  private AuthRoleService authRoleService;

  public UserAuthorities getUserAuthorities(Long userId) {
    final List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
    final List<Long> roleIdList = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
    final List<AuthRoleElementOperation> authRoleElementOperationList = authRoleService.getRoleElementOperationsByRoleIds(roleIdList);
    final List<AuthRoleMenu> authRoleMenuList = authRoleService.getAuthRoleMenusByRoleIds(roleIdList);
    final UserAuthorities userAuthorities = UserAuthorities
        .builder()
        .roleElementOperationList(authRoleElementOperationList)
        .roleMenuList(authRoleMenuList)
        .build();
    return userAuthorities;
  }
}
