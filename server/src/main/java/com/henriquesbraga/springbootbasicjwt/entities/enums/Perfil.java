package com.henriquesbraga.springbootbasicjwt.entities.enums;

public enum Perfil {

  ADMIN(1, "ROLE_ADMIN"),
  NORMAL(2, "ROLE_NORMAL");

  private Integer codigo;
  private String descricao;

  private Perfil(int codigo, String descricao){
    this.codigo = codigo;
    this.descricao = descricao;
  }

  public static Perfil toEnum(Integer codigo){
    if(codigo == null){
      return null;
    }
    for(Perfil x : Perfil.values()){
      if(codigo.equals(x.getCodigo())){
        return x;
      }
    }
    throw new IllegalArgumentException("ID Inválido: " + codigo);
  }

  public Integer getCodigo() {
    return codigo;
  }

  public void setCodigo(Integer codigo) {
    this.codigo = codigo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }
}
