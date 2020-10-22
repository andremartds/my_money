package com.andremartds.despesas_pessoais.repository;

import com.andremartds.despesas_pessoais.model.Pessoa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
