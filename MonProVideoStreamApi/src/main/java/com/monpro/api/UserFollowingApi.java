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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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

  @PostMapping("/user-following-groups")
  public JsonResponse<Long> addUserFollowingGroups(final @RequestBody FollowingGroup followingGroup) {
    final Long userId = userSupport.getCurrentUserId();
    followingGroup.setUserId(userId);
    final Long groupId = userFollowingService.addUserFollowingGroup(followingGroup);
    return new JsonResponse<>(groupId);
  }

  @GetMapping("/user-following-groups")
  public JsonResponse<List<FollowingGroup>> getUserFollowingGroups() {
    final Long userId = userSupport.getCurrentUserId();
    final List<FollowingGroup> list = userFollowingService.getUserFollowingGroups(userId);
    return new JsonResponse<>(list);
  }
}
