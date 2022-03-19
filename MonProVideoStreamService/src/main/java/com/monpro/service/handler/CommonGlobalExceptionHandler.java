package com.monpro.service.handler;


import com.monpro.domain.JsonResponse;
import com.monpro.domain.exception.ConditionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonGlobalExceptionHandler {

  public static final String COMMON_ERROR_CODE = "500";
  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public JsonResponse<String> commonExceptionHandler(final HttpServletRequest request, final Exception exception) {
    final String errorMsg = exception.getMessage();
    if (exception instanceof ConditionException) {
      final String errorCode = ((ConditionException) exception).getCode();
      return new JsonResponse<>(errorCode, errorMsg);
    } else {
      return new JsonResponse<>(COMMON_ERROR_CODE, errorMsg);
    }
  }
}
