package com.monpro.service.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonResponse<T> {

  public static final String SUCCESS = "Success";
  public static final String FAILURE = "Failure";
  public static final String SUCCESS_CODE = "0";
  public static final String FAILURE_CODE = "1";

  private String code;

  private String msg;

  private T data;

  public JsonResponse(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public JsonResponse(T data) {
    this.msg = SUCCESS;
    this.code = SUCCESS_CODE;
    this.data = data;
  }

  public static JsonResponse<String> success() {
    return new JsonResponse<>(null);
  }

  public static JsonResponse<String> success(final String data) {
    return new JsonResponse<>(data);
  }

  public static JsonResponse<String> fail() {
    return new JsonResponse<>(FAILURE_CODE, FAILURE);
  }

  public static JsonResponse<String> fail(final String code, final String msg) {
    return new JsonResponse<>(code, msg);
  }
}
