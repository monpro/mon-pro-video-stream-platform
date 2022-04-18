package com.monpro.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class User {

  private Long id;

  private String phone;

  private String email;

  private String password;

  private String salt;

  private Date createTime;

  private Date updateTime;

  private UserInfo userInfo;

}
