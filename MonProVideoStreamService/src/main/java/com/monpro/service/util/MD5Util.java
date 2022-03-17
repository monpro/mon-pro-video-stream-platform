package com.monpro.service.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * MD5 Encryption
 * The MD5 message-digest algorithm is a cryptographically broken but still widely used hash function producing a 128-bit hash value.
 */
public class MD5Util {

  public static String sign(final String content, final String salt, final String charset) {
    return DigestUtils.md5Hex(getContentBytes(content + salt, charset));
  }

  public static boolean verify(final String content, final String sign, final String salt, final String charset) {
    String md5HexString = DigestUtils.md5Hex(getContentBytes(content + salt, charset));
    return md5HexString.equals(sign);
  }

  private static byte[] getContentBytes(final String content, final String charset) {
    if ("".equals(charset)) {
      return content.getBytes();
    } else {
      try {
        return content.getBytes(charset);
      } catch (UnsupportedEncodingException var3) {
        throw new RuntimeException("named charset is not supported:", var3);
      }
    }
  }

  public static String getFileMD5(final MultipartFile file) throws Exception {
    InputStream fis = file.getInputStream();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int byteRead;
    while((byteRead = fis.read(buffer)) > 0){
      byteArrayOutputStream.write(buffer, 0, byteRead);
    }
    fis.close();
    return DigestUtils.md5Hex(byteArrayOutputStream.toByteArray());
  }
}