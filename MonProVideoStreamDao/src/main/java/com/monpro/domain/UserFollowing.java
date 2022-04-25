package com.monpro.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFollowing {

  private Long id;

  private Long userId;

  private Long followingId;

  private Long groupId;

  private Date createTime;

  private UserInfo userInfo;

}
