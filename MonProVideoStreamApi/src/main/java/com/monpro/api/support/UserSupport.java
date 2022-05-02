package com.monpro.api.support;

import com.monpro.domain.exception.ConditionException;
import com.monpro.service.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Slf4j
public class UserSupport {

  public Long getCurrentUserId() {
    final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    final String token = attributes.getRequest().getHeader("token");
    final Long userId = TokenUtil.verifyToken(token);
    if (userId < 0) {
      throw new ConditionException("Illegal Token");
    }
    return userId;
  }

}
