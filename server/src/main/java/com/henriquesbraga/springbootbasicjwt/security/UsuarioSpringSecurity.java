package com.henriquesbraga.springbootbasicjwt.security;

import com.henriquesbraga.springbootbasicjwt.entities.enums.Perfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UsuarioSpringSecurity implements UserDetails {

  private Long id;
  private String email;
  private String senha;
  private Collection<? extends GrantedAuthority> authorities;

  public UsuarioSpringSecurity(){
  }

  public UsuarioSpringSecurity(Long id, String email, String senha, Set<Perfil> perfis) {
    super();
    this.id = id;
    this.email = email;
    this.senha = senha;
    this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
  }

  public Long getId() {
    return id;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return senha;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  } //Conta não expirada

  @Override
  public boolean isAccountNonLocked() {
    return true;
  } //Conta não bloqueada

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  } //Credenciais não expiradas

  @Override
  public boolean isEnabled() {
    return true;
  } //Usuário está ativo

  public boolean hasRole(Perfil perfil) {
    //converte o perfil para grantedAutoritites e verifica se o perfil pertence a lista de authorities
    return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
  }

}
