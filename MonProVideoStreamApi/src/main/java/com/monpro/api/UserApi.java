package com.monpro.api;


import com.monpro.api.support.UserSupport;
import com.monpro.dao.User;
import com.monpro.domain.JsonResponse;
import com.monpro.service.UserService;
import com.monpro.service.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserApi {

  @Autowired
  private UserService userService;

  @Autowired
  private UserSupport userSupport;

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
}
