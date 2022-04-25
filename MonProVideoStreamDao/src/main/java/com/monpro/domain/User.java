package com.monpro.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
