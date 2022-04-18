package com.monpro.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class UserFollowing {

  private Long id;

  private Long userId;

  private Long followingId;

  private Long groupId;

  private Date createTime;

  private UserInfo userInfo;

}
