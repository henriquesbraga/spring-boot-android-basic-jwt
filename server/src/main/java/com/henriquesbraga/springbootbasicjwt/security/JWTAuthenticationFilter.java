package com.henriquesbraga.springbootbasicjwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henriquesbraga.springbootbasicjwt.entities.dto.CredenciaisDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  private JWTUtil jwtUtil;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
    setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req,
                                              HttpServletResponse res) throws AuthenticationException {

    try {
      CredenciaisDTO creds = new ObjectMapper()
              .readValue(req.getInputStream(), CredenciaisDTO.class); //Instanciando CredenciaisDTO com os dados da requisição

      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());

      Authentication auth = authenticationManager.authenticate(authToken);    //Verifica se os dados são validos
      return auth;
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req,
                                          HttpServletResponse res,
                                          FilterChain chain,
                                          Authentication auth) throws IOException, ServletException {

    String username = ((UsuarioSpringSecurity) auth.getPrincipal()).getUsername();
    String token = jwtUtil.generateToken(username);

    String responseToken = "{\"token\": " + "\"" + token + "\"" +"}";
    PrintWriter out = res.getWriter();
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");
    out.print(responseToken);
    out.flush();


  }

  private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
      response.setStatus(401);
      response.setContentType("application/json");
      response.getWriter().append(json());
    }

    private String json() {
      long date = new Date().getTime();
      return "{\"timestamp\": " + date + ", "
              + "\"status\": 401, "
              + "\"error\": \"Não autorizado\", "
              + "\"message\": \"Email ou senha inválidos\", "
              + "\"path\": \"/login\"}";
    }
  }
}