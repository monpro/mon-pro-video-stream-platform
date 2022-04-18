package com.monpro.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class FollowingGroup {

  private Long id;

  private Long userId;

  private String name;

  private String type;

  private Date createTime;

  private Date updateTime;

  private List<UserInfo> userInfoList;
}
