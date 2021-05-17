package com.henriquesbraga.springbootbasicjwt.security;

import com.henriquesbraga.springbootbasicjwt.services.exceptions.AuthorizationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  private JWTUtil jwtUtil;
  private UserDetailsService userDetailsService;

  public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
    super(authenticationManager);
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService; //Filtro analisa o token pra saber se é valido. extrai o user
  }

  //Middleware
  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain chain) throws IOException, ServletException {

    String header = request.getHeader("Authorization"); //Pega o valor que estiver nesse header
    if (header != null && header.startsWith("Bearer ")) {     //Testa para saber se veio com o Bearer
      UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7)); //manda o token descontando Bearer .
      if (auth != null) {     //testa se auth é diferente de nulo e libera o acesso
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }
    chain.doFilter(request, response);  //next();
  }

  private UsernamePasswordAuthenticationToken getAuthentication(String token) {
    if (jwtUtil.tokenValido(token)) {                                          //se o token for valido, está autorizado
      String username = jwtUtil.getUsername(token);                           //pega o username dentro do token
      UserDetails user = userDetailsService.loadUserByUsername(username);     //busca no DB por esse ususario
      return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());    //retorna o com o user, null e getAuthorities
    }
    return null;
  }



}

