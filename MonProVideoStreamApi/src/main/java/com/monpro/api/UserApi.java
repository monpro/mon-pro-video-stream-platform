package com.monpro.api;


import com.monpro.dao.User;
import com.monpro.domain.JsonResponse;
import com.monpro.service.UserService;
import com.monpro.service.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApi {

  @Autowired
  private UserService userService;

  @GetMapping("/rsa-pks")
  public JsonResponse<String> getRsaPublicKey() {
    return new JsonResponse<>(RSAUtil.getPublicKeyString());
  }

  @PostMapping("/users")
  public JsonResponse<String> addUser(@RequestBody final User user) {
    userService.addUser(user);
    return JsonResponse.success();
  }
}
