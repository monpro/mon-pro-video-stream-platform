package com.monpro.service.config;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JsonHttpMsgConverterConfig {


  @Bean
  @Primary
  public HttpMessageConverters fastJsonHttpMessageConverters() {
    final FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
    final FastJsonConfig fastJsonConfig = new FastJsonConfig();
    fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
    fastJsonConfig.setSerializerFeatures(
        SerializerFeature.PrettyFormat,
        SerializerFeature.WriteNullStringAsEmpty,
        SerializerFeature.WriteNullListAsEmpty,
        SerializerFeature.WriteMapNullValue,
        SerializerFeature.MapSortField,
        SerializerFeature.DisableCircularReferenceDetect
    );
    converter.setFastJsonConfig(fastJsonConfig);
    return new HttpMessageConverters(converter);
  }
}
