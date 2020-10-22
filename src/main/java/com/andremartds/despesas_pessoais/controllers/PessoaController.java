package com.andremartds.despesas_pessoais.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.andremartds.despesas_pessoais.evento.RecursoCriadoEvent;
import com.andremartds.despesas_pessoais.model.Pessoa;
import com.andremartds.despesas_pessoais.repository.PessoaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@Api("API REST PESSOA")
@RestController
@RequestMapping("/pessoa")
public class PessoaController {

  @Autowired
  PessoaRepository pessoaRepository;

  @Autowired
  ApplicationEventPublisher publisher;

  @ApiOperation(value = "Metodo para listar todas as pessoas")
  @GetMapping
  public List<Pessoa> todosUsuarios() {
    return pessoaRepository.findAll();
  }

  @GetMapping("/{codigo}")
  @ApiOperation(value = "Retornando uma pessoa por código")
  public ResponseEntity<Pessoa> buscarPorCodigo(@PathVariable Long codigo) {
    return this.pessoaRepository.findById(codigo).map(pessoa -> ResponseEntity.ok(pessoa))
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  @ApiOperation(value = "Adicionando uma pessoa utilizando validação no mesmo contexto")
  public ResponseEntity<Pessoa> addPessoa(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
    Pessoa pessoaSalva = pessoaRepository.save(pessoa);
    this.publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
    // URI uri =
    // ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
    // .buildAndExpand(pessoaSalva.getCodigo()).toUri();

    return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
  }

  @DeleteMapping("/{codigo}")
  @ApiOperation(value = "Removendo uma pessoa")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removePessoa(@PathVariable Long codigo) {
    pessoaRepository.deleteById(codigo);
  }

}
