package com.monpro.service.util;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AESUtilTest {

  private static AESUtil aesUtil;

  @BeforeAll
  static void Init_TestEnvironment() throws Exception {
    aesUtil = new AESUtil("randomSeed");
  }

  @Test
  void GivenContent_WhenEncryptAndDecrypt_Then_GetSameContentBack() throws Exception {
    final String content = "testContent";
    final String encrypt = aesUtil.encrypt(content);
    assertEquals(content, aesUtil.decrypt(encrypt));
  }

  @Test
  void WhenGetSecretKey_Then_GetValidSecretKey() throws Exception {
    final SecretKey secretKey = aesUtil.getSecretKey();
    assertNotNull(secretKey);
    assertEquals(secretKey.getAlgorithm(), AESUtil.KEY_ALGORITHM);
  }
}
