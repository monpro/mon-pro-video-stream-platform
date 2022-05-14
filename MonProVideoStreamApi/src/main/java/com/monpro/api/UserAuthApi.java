package com.monpro.api;


import com.monpro.api.support.UserSupport;
import com.monpro.domain.JsonResponse;
import com.monpro.domain.auth.UserAuthorities;
import com.monpro.service.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserAuthApi {

  @Autowired
  private UserSupport userSupport;

  @Autowired
  private UserAuthService userAuthService;

  @GetMapping("/user-authorities")
  public JsonResponse<UserAuthorities> getUserAuthorities() {
    final Long userId = userSupport.getCurrentUserId();
    final UserAuthorities userAuthorities = userAuthService.getUserAuthorities(userId);
    log.info(String.valueOf(userAuthorities));
    return new JsonResponse<>(userAuthorities);
  }
}

