package com.monpro.service;

import com.monpro.domain.auth.UserAuthorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

  @Autowired
  private UserRoleService userRoleService;

  @Autowired
  private AuthRoleService authRoleService;

  public UserAuthorities getUserAuthorities(Long userId) {
    return null;
  }
}
