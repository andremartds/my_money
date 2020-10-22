package com.andremartds.despesas_pessoais.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import com.andremartds.despesas_pessoais.model.embed.Endereco;

import javax.validation.constraints.NotNull;

@Entity
public class Pessoa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long codigo;

  @NotNull
  @Size(min = 3, max = 50)
  private String nome;

  @Embedded
  private Endereco endereco;

  private Boolean ativo;

  public String getNome() {
    return nome;
  }

  public Boolean getAtivo() {
    return ativo;
  }

  public Long getCodigo() {
    return codigo;
  }

  public Endereco getEndereco() {
    return endereco;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setEndereco(Endereco endereco) {
    this.endereco = endereco;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public void setAtivo(Boolean ativo) {
    this.ativo = ativo;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Pessoa other = (Pessoa) obj;
    if (codigo == null) {
      if (other.codigo != null)
        return false;
    } else if (!codigo.equals(other.codigo))
      return false;
    return true;
  }
}
