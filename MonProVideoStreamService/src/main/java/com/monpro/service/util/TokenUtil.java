package com.monpro.service.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;

public class TokenUtil {

  private static final String DEFAULT_ISSUER = "DEFAULT_ISSUER";
  private static final Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());

  public static String getToken(final Long userId) {
    final Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.SECOND, 30);
    return JWT.create().withKeyId(String.valueOf(userId))
        .withIssuer(DEFAULT_ISSUER)
        .withExpiresAt(calendar.getTime())
        .sign(algorithm);
  }

  public static Long verifyToken(final String token) {
    final JWTVerifier jwtVerifier = JWT.require(algorithm).build();
    final DecodedJWT jwt = jwtVerifier.verify(token);
    return Long.valueOf(jwt.getKeyId());
  }

}
