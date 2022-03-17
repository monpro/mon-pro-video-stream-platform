package com.monpro.api;

import com.monpro.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoApi {

  @Autowired
  private DemoService demoService;

  @GetMapping("/query")
  public String query(final Long id) {
    return demoService.query(id) + "test";
  }
}
