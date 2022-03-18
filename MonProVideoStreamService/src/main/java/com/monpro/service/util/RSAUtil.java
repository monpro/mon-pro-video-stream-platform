package com.monpro.service.util;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.ResourceUtils;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;

/**
 * RSA Util class.
 * the encryption key is public and distinct from the decryption key, which is kept secret (private).
 */

@Slf4j
public class RSAUtil {

  private static final Properties properties = fetchProperties();

  private static final String PUBLIC_KEY = (String) properties.get("rsa.public.key");

  private static final String PRIVATE_KEY = (String) properties.get("rsa.private.key");

  private static final String RSA = "RSA";

  private static Properties fetchProperties(){
    Properties properties = new Properties();
    try {
      File file = ResourceUtils.getFile("classpath:application.properties");
      InputStream in = new FileInputStream(file);
      properties.load(in);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return properties;
  }
  public static RSAPublicKey getPublicKey() throws Exception {
    byte[] decoded = Base64.decodeBase64(PUBLIC_KEY);
    return (RSAPublicKey) KeyFactory.getInstance(RSA)
        .generatePublic(new X509EncodedKeySpec(decoded));
  }

  public static RSAPrivateKey getPrivateKey() throws Exception {
    byte[] decoded = Base64.decodeBase64(PRIVATE_KEY);
    return (RSAPrivateKey) KeyFactory.getInstance(RSA)
        .generatePrivate(new PKCS8EncodedKeySpec(decoded));
  }

  public static RSAKey generateKeyPair() throws NoSuchAlgorithmException {
    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA);
    keyPairGen.initialize(1024, new SecureRandom());
    KeyPair keyPair = keyPairGen.generateKeyPair();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
    String privateKeyString = new String(Base64.encodeBase64(privateKey.getEncoded()));
    return new RSAKey(privateKey, privateKeyString, publicKey, publicKeyString);
  }

  public static String encrypt(final String source) throws Exception {
    byte[] decoded = Base64.decodeBase64(PUBLIC_KEY);
    RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance(RSA)
        .generatePublic(new X509EncodedKeySpec(decoded));
    Cipher cipher = Cipher.getInstance(RSA);
    cipher.init(1, rsaPublicKey);
    return Base64.encodeBase64String(cipher.doFinal(source.getBytes(StandardCharsets.UTF_8)));
  }

  public static Cipher getCipher() throws Exception {
    byte[] decoded = Base64.decodeBase64(PRIVATE_KEY);
    RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance(RSA)
        .generatePrivate(new PKCS8EncodedKeySpec(decoded));
    Cipher cipher = Cipher.getInstance(RSA);
    cipher.init(2, rsaPrivateKey);
    return cipher;
  }

  public static String decrypt(final String text) throws Exception {
    Cipher cipher = getCipher();
    byte[] inputByte = Base64.decodeBase64(text.getBytes(StandardCharsets.UTF_8));
    return new String(cipher.doFinal(inputByte));
  }

  @Getter
  @Setter
  @AllArgsConstructor
  public static class RSAKey {
    private RSAPrivateKey privateKey;
    private String privateKeyString;
    private RSAPublicKey publicKey;
    public String publicKeyString;
  }

}