package com.monpro.service.util;

import org.junit.jupiter.api.Test;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RSAUtilTest {

  @Test
  void WhenGetPublicKey_ThenGetValidRSAKey() throws Exception {
    final RSAPublicKey publicKey = RSAUtil.getPublicKey();
    assertEquals(publicKey.getAlgorithm(), "RSA");
  }

  @Test
  void WhenGetPrivateKey_ThenGetValidRSAKey() throws Exception {
    final RSAPrivateKey privateKey = RSAUtil.getPrivateKey();
    assertEquals(privateKey.getAlgorithm(), "RSA");
  }

  @Test
  void WhenGenerateKeyPair_ThenGetValidRSAKey() throws Exception {
    final RSAUtil.RSAKey rsaKey = RSAUtil.generateKeyPair();
    assertEquals(rsaKey.getPrivateKey().getAlgorithm(), "RSA");
    assertEquals(rsaKey.getPublicKey().getAlgorithm(), "RSA");
  }

  @Test
  void GivenSource_WhenEncryptAndDecrypt_ThenGetSameTextBack() throws Exception {
    final String source = "testSource";
    assertEquals(source, RSAUtil.decrypt(RSAUtil.encrypt(source)));
  }
}
