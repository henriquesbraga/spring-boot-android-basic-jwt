package com.henriquesbraga.springbootbasicjwt.config;


import com.henriquesbraga.springbootbasicjwt.security.JWTAuthenticationFilter;
import com.henriquesbraga.springbootbasicjwt.security.JWTAuthorizationFilter;
import com.henriquesbraga.springbootbasicjwt.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JWTUtil jwtUtil;

  @Autowired
  private Environment env;

  @Autowired
  //Para dizer quem vai ser o cara que é capaz de buscar um user por email
  private UserDetailsService userDetailsService;

  //Rotas Publicas
  private static final String[] PUBLIC_MATCHERS = {
    "/h2-console/**",
  };

  private static final String[] PUBLIC_MATCHERS_GET ={
    "/usuarios/test",
  };

  private static final String[] PUBLIC_MATCHERS_POST ={
    "/usuarios/signup",
  };


  @Override
  protected void configure(HttpSecurity http) throws Exception{

    if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
      http.headers().frameOptions().disable();
    }

    http.cors().and().csrf().disable();               //desabilita o csrf
    http.authorizeRequests()
    .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
    .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() //Só permite get nas rotas dessa lista
    .antMatchers(PUBLIC_MATCHERS).permitAll()        //Libera as rotas daquele vetor
    .anyRequest().authenticated();
    http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
    http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //Não cria sessão de usuario
  }

  //Para dizer quem é u userdetailservice e quem é o algol de codificação da senha
  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
    configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder(){ //Bean para poder injetar em qualquer classe
    return new BCryptPasswordEncoder();
  }


  
}
