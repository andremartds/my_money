package com.andremartds.despesas_pessoais.model.embed;

import javax.persistence.Embeddable;

@Embeddable
public class Endereco {
  
  private String  logradouro;
  private Integer numero;
  private String  bairro;
  private String  cep;
  private String  cidade;
  private String  estado;

  public String getBairro() {
    return bairro;
  }

  public String getCep() {
    return cep;
  }

  public String getCidade() {
    return cidade;
  }

  public String getEstado() {
    return estado;
  }

  public String getLogradouro() {
    return logradouro;
  }

  public Integer getNumero() {
    return numero;
  }

  public void setBairro(String bairro) {
    this.bairro = bairro;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }

  public void setCidade(String cidade) {
    this.cidade = cidade;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public void setLogradouro(String logradouro) {
    this.logradouro = logradouro;
  }

  public void setNumero(Integer numero) {
    this.numero = numero;
  }
}
