package com.monpro.dao;

import com.monpro.domain.auth.AuthRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthRoleMenuDao {
  List<AuthRoleMenu> getAuthRoleMenusByRoleIds(List<Long> roleIdList);
}
