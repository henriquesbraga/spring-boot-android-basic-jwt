package com.henriquesbraga.springbootbasicjwt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.henriquesbraga.springbootbasicjwt.entities.enums.Perfil;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "USUARIO")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
  @SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq", allocationSize = 1, initialValue = 1)
  private Long id;

  @Column(unique = true)
  private String email;

  private String nome;

  @JsonIgnore // Para não aparecer no JSON
  private String senha;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PERFIS")
  private Set<Integer> perfis = new HashSet<>();

  public Usuario() {
    adicionaPerfil(Perfil.NORMAL);
  }

  public Usuario(Long id, String email, String nome, String password) {
    this.id = id;
    this.email = email;
    this.nome = nome;
    this.senha = password;
  }

  public void adicionaPerfil(Perfil perfil){
    perfis.add(perfil.getCodigo());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String password) {
    this.senha = password;
  }

  public Set<Perfil> getPerfis() {
    return perfis.stream().map((x) -> Perfil.toEnum(x)).collect(Collectors.toSet());
  }

  public void setPerfis(Set<Integer> perfis) {
    this.perfis = perfis;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Usuario usuario = (Usuario) o;
    return id.equals(usuario.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
