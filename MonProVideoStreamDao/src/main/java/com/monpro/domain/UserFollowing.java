package com.monpro.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserFollowing {

  private Long id;

  private Long userId;

  private Long followingId;

  private Long groupId;

  private Date createTime;

}
