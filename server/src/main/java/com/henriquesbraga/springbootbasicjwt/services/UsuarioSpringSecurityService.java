package com.henriquesbraga.springbootbasicjwt.services;

import com.henriquesbraga.springbootbasicjwt.security.UsuarioSpringSecurity;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsuarioSpringSecurityService {
  public static UsuarioSpringSecurity authenticated(){
    try{
      return (UsuarioSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    catch (Exception e){
      e.printStackTrace();
      return null;
    }
  }
}
