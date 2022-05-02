package com.monpro.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMoment {

  private Long id;

  private Long userId;

  private String type;

  private Long contentId;

  private Date createTime;

  private Date updateTime;

}
