package com.henriquesbraga.springbootbasicjwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {
  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long expiration;

  public String generateToken(String username) {
    return Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret.getBytes())  //Como vai assinar o token e o segredo
            .compact();
  }

  /*
    Claims é um tipo do jwt que armazena as reividicações do token.
    o user esá reinvindicando o user e o tempo de expiração.
    a pessoa que envia está alegando que é o user tal e que o tempo de expiração e tal.
    fica armazenado no Claims.
   */
  public boolean tokenValido(String token){
    Claims claims = getClaims(token);
    //Testa se o claims for != null, deu certo
    if(claims != null) {
      String username = claims.getSubject();//retorna user
      Date expirationDate = claims.getExpiration(); //pega a data de expiração
      Date now = new Date(System.currentTimeMillis()); //Data atual para testar se o token está expirado

      //Se username != null, expirationDate != null e o instante atual é anterior a data de expiração = o token está valido
      if (username != null && expirationDate != null && now.before(expirationDate)) {
        return true;
      }
    }
    return false;
  }

  public String getUsername(String token) {
    Claims claims = getClaims(token);
    //Testa se o claims for != null, deu certo
    if (claims != null) {
      return claims.getSubject();//retorna user
    }
    return null;
  }

  private Claims getClaims(String token) {
    //Tentou pegar os claims, o token é invalido? retorna null
    try {
      return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();  //Recupera Claims a partir de um token
    }
    catch (Exception e){
      return null;
    }
  }
}
