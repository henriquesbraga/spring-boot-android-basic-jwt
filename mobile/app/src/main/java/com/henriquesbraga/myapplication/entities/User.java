package com.henriquesbraga.myapplication.entities;

import com.google.gson.annotations.Expose;

public class User {

  @Expose
  private String email;
  @Expose
  private String senha;
  @Expose
  private String token;

  public User() {
  }

  public User(String email, String senha) {
    this.email = email;
    this.senha = senha;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return "User{" +
            "email='" + email + '\'' +
            ", senha='" + senha + '\'' +
            ", token='" + token + '\'' +
            '}';
  }
}
