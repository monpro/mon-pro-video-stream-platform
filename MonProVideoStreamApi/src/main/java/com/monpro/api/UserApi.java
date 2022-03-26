package com.monpro.api;


import com.monpro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApi {

  @Autowired
  private UserService userService;


}
