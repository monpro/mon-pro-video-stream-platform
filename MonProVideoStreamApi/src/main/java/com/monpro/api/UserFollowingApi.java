package com.monpro.api;

import com.monpro.api.support.UserSupport;
import com.monpro.domain.FollowingGroup;
import com.monpro.domain.JsonResponse;
import com.monpro.domain.UserFollowing;
import com.monpro.service.UserFollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class UserFollowingApi {

  @Autowired
  private UserFollowingService userFollowingService;

  @Autowired
  private UserSupport userSupport;

  @PostMapping("/user-followings")
  public JsonResponse<String> addUserFollowings(final @RequestBody UserFollowing userFollowing) {
    final Long userId = userSupport.getCurrentUserId();
    userFollowing.setUserId(userId);
    userFollowingService.addUserFollowing(userFollowing);
    return JsonResponse.success();
  }

  @GetMapping("/user-followings")
  public JsonResponse<List<FollowingGroup>> getUserFollowings() {
    final Long userId = userSupport.getCurrentUserId();
    final List<FollowingGroup> result = userFollowingService.getUserFollowings(userId);
    return new JsonResponse<>(result);
  }

  @GetMapping("/user-fans")
  public JsonResponse<List<UserFollowing>> getUserFans() {
    final Long userId = userSupport.getCurrentUserId();
    final List<UserFollowing> result = userFollowingService.getUserFans(userId);
    return new JsonResponse<>(result);
  }
}
