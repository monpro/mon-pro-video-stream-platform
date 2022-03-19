package com.monpro.service.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonHttpMsgConverterConfigTest {

  private static final JsonHttpMsgConverterConfig config = new JsonHttpMsgConverterConfig();
  private static final HttpMessageConverters converter = config.fastJsonHttpMessageConverters();


  @Test
  void GivenConverters_WhenGetConverter_ThenReturnExpectedResult() {
    final List<HttpMessageConverter<?>> converters = converter.getConverters();
    assertTrue(converters.size() > 0);
  }
}
