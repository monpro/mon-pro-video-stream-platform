package com.monpro.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

  private Long id;

  private Long userId;

  private String nick;

  private String avatar;

  private String sign;

  private String gender;

  private String birth;

  private Date createTime;

  private Date updateTime;

  private Boolean followed;

}
