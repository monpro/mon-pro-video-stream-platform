package com.monpro.dao;

import com.monpro.domain.UserMoment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMomentDao {

  Integer addUserMoments(UserMoment userMoment);
}
