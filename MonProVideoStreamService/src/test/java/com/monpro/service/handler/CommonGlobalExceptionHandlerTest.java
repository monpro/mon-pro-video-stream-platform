package com.monpro.service.handler;

import com.monpro.domain.JsonResponse;
import com.monpro.domain.exception.ConditionException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;

import static com.monpro.service.handler.CommonGlobalExceptionHandler.COMMON_ERROR_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonGlobalExceptionHandlerTest {

  private static final CommonGlobalExceptionHandler handler = new CommonGlobalExceptionHandler();

  @Mock
  private HttpServletRequest httpServletRequest;

  @Test
  void GivenConditionException_WhenCallCommonExceptionHandler_ThenReturnExpectedResult() {
    final String testErrorCode = "999";
    final ConditionException conditionException = new ConditionException(testErrorCode, "testName");
    final JsonResponse<String> stringJsonResponse = handler.commonExceptionHandler(httpServletRequest, conditionException);
    assertEquals(stringJsonResponse.getCode(), testErrorCode);
  }

  @Test
  void GivenNonConditionException_WhenCallCommonExceptionHandler_ThenReturnExpectedResult() {
    final RuntimeException runtimeException = new RuntimeException("testName");
    final JsonResponse<String> stringJsonResponse = handler.commonExceptionHandler(httpServletRequest, runtimeException);
    assertEquals(stringJsonResponse.getCode(), COMMON_ERROR_CODE);
  }
}
