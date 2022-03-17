package com.monpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MonProVideoStreamApiApp {

  public static void main(String[] args) {
    final ApplicationContext app = SpringApplication.run(MonProVideoStreamApiApp.class, args);
  }
}
