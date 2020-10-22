package com.andremartds.despesas_pessoais.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andremartds.despesas_pessoais.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
