package com.monpro.domain.exception;


import lombok.Getter;
import lombok.Setter;

public class ConditionException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  private String code;

  public ConditionException(final String code, final String name) {
    super(name);
    this.code = code;
  }

  public ConditionException(final String name) {
    super(name);
    this.code = "500";
  }
}
