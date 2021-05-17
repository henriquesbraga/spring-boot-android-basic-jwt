package com.henriquesbraga.springbootbasicjwt.entities.dto;

import com.henriquesbraga.springbootbasicjwt.entities.Usuario;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UsuarioDTO {

  private Long id;

  @NotEmpty(message="Preenchimento obrigatório")
  @Email(message="Email inválido")
  private String nome;

  @NotEmpty(message="Preenchimento obrigatório")
  @Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")
  private String email;

  public UsuarioDTO() {
  }

  public UsuarioDTO(Usuario obj){
    this.id = obj.getId();
    this.nome = obj.getNome();
    this.email = obj.getEmail();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String name) {
    this.nome = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
