package com.monpro.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PageResult<T> {

  private Integer total;

  private List<T> list;

}
