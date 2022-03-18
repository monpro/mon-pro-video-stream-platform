package com.monpro.service.domain;

import org.junit.jupiter.api.Test;

import static com.monpro.service.domain.JsonResponse.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonResponseTest {

  @Test
  void WhenSuccess_ThenReturnExpectedJsonResponse() {
    final JsonResponse<String> jsonResponse = JsonResponse.success();
    assertEquals(jsonResponse.getCode(), SUCCESS_CODE);
    assertEquals(jsonResponse.getMsg(), SUCCESS);
  }

  @Test
  void WhenSuccessWithDataParam_ThenReturnExpectedJsonResponse() {
    final JsonResponse<String> jsonResponse = JsonResponse.success("testData");
    assertEquals(jsonResponse.getCode(), SUCCESS_CODE);
    assertEquals(jsonResponse.getMsg(), SUCCESS);
    assertEquals(jsonResponse.getData(), "testData");
  }

  @Test
  void WhenFail_ThenReturnExpectedJsonResponse() {
    final JsonResponse<String> jsonResponse = JsonResponse.fail();
    assertEquals(jsonResponse.getCode(), FAILURE_CODE);
    assertEquals(jsonResponse.getMsg(), FAILURE);
  }

  @Test
  void WhenFailWithParams_ThenReturnExpectedJsonResponse() {
    final String testCode = "testCode", testMsg = "testMsg";
    final JsonResponse<String> jsonResponse = JsonResponse.fail(testCode, testMsg);
    assertEquals(jsonResponse.getCode(), testCode);
    assertEquals(jsonResponse.getMsg(), testMsg);
  }
}
