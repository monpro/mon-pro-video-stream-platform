package com.monpro.api;

import com.monpro.api.support.UserSupport;
import com.monpro.domain.JsonResponse;
import com.monpro.domain.UserMoment;
import com.monpro.service.UserMomentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Slf4j
public class UserMomentApi {

  @Autowired
  private UserMomentService userMomentsService;

  @Autowired
  private UserSupport userSupport;

  @PostMapping("/user-moments")
  public JsonResponse<String> addUserMoments(@RequestBody final UserMoment userMoment) throws Exception {
    final Long userId = userSupport.getCurrentUserId();
    userMoment.setUserId(userId);
    userMomentsService.addUserMoments(userMoment);
    return JsonResponse.success();
  }
}
