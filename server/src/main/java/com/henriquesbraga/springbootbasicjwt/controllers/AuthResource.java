package com.henriquesbraga.springbootbasicjwt.controllers;

import com.henriquesbraga.springbootbasicjwt.security.JWTUtil;
import com.henriquesbraga.springbootbasicjwt.security.UsuarioSpringSecurity;
import com.henriquesbraga.springbootbasicjwt.services.UsuarioSpringSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

  @Autowired
  private JWTUtil jwtUtil;

  @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
  public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
    UsuarioSpringSecurity user = UsuarioSpringSecurityService.authenticated();
    String token = jwtUtil.generateToken(user.getUsername());
    response.addHeader("Authorization", "Bearer " + token);
    response.addHeader("access-control-expose-headers", "Authorization");
    return ResponseEntity.noContent().build();
  }


}
