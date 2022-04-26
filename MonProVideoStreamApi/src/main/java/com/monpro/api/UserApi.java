package com.monpro.api;


import com.alibaba.fastjson.JSONObject;
import com.monpro.api.support.UserSupport;
import com.monpro.domain.PageResult;
import com.monpro.domain.User;
import com.monpro.domain.JsonResponse;
import com.monpro.domain.UserInfo;
import com.monpro.service.UserFollowingService;
import com.monpro.service.UserService;
import com.monpro.service.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserApi {

  @Autowired
  private UserService userService;

  @Autowired
  private UserSupport userSupport;

  @Autowired
  private UserFollowingService userFollowingService;

  @GetMapping("/users")
  public JsonResponse<User> getUserData() {
    final Long currentUserId = userSupport.getCurrentUserId();
    final User user = userService.getUserData(currentUserId);
    return new JsonResponse<>(user);
  }

  @GetMapping("/rsa-pks")
  public JsonResponse<String> getRsaPublicKey() {
    return new JsonResponse<>(RSAUtil.getPublicKeyString());
  }

  @PostMapping("/users")
  public JsonResponse<String> addUser(@RequestBody final User user) {
    userService.addUser(user);
    return JsonResponse.success();
  }

  @PostMapping("/login")
  public JsonResponse<String> login(@RequestBody final User user) {
    return new JsonResponse<>(userService.login(user));
  }

  @PutMapping("/users")
  public JsonResponse<String> updateUsers(@RequestBody final User user) {
    final Long currentUserId = userSupport.getCurrentUserId();
    user.setId(currentUserId);
    userService.updateUser(user);
    return JsonResponse.success();
  }

  @PutMapping("/user-infos")
  public JsonResponse<String> updateUserInfos(@RequestBody final UserInfo userInfo) {
    final Long currentUserId = userSupport.getCurrentUserId();
    userInfo.setUserId(currentUserId);
    userService.updateUserInfo(userInfo);
    return JsonResponse.success();
  }

  @GetMapping("/user-infos")
  public JsonResponse<PageResult<UserInfo>> pageListUserInfos(@RequestParam final Integer num, @RequestParam final Integer size, final String nick) {
    final Long currentUserId = userSupport.getCurrentUserId();
    final JSONObject params = new JSONObject();
    params.put("num", num);
    params.put("size", size);
    params.put("nick", nick);
    params.put("userId", currentUserId);
    final PageResult<UserInfo> result = userService.pageListUserInfos(params);
    if (result.getTotal() > 0) {
      final List<UserInfo> checkedUserInfoList = userFollowingService.checkFollowingStatus(result.getList(), currentUserId);
      result.setList(checkedUserInfoList);
    }
    return new JsonResponse<>(result);
  }
}
