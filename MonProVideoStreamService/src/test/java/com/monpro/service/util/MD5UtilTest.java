package com.monpro.service.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MD5UtilTest {

  @Test
  void GivenContentSaltCharset_WhenSignAndVerify_ThenGetSameResults() {
    final String content = "testContent";
    final String salt = "testSalt";
    final String charset = "UTF-8";
    final String sign = MD5Util.sign(content, salt, charset);

    assertTrue(MD5Util.verify(content, sign, salt, charset));
  }


}
