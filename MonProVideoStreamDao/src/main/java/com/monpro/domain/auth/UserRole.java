package com.monpro.domain.auth;

import lombok.*;

import java.util.Date;

@Data
@Builder
public class UserRole {

    private Long id;

    private Long userId;

    private Long roleId;

    private String roleName;

    private String roleCode;

    private Date createTime;
}
