package com.andremartds.despesas_pessoais.controllers;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.andremartds.despesas_pessoais.evento.RecursoCriadoEvent;
import com.andremartds.despesas_pessoais.model.Categoria;
import com.andremartds.despesas_pessoais.repository.CategoriaRepository;

@CrossOrigin(origins = "*")
@Api("API REST CATEGORIA")
@RestController
@RequestMapping("/categoria")
public class CategoriaController {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	ApplicationEventPublisher publisher;

	@ApiOperation(value = "Retornando todas as categorias")
	@GetMapping
	public List<Categoria> allCategoria() {
		return categoriaRepository.findAll();
	}

	@ApiOperation(value = "Retornando categoria por código")
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPorCodigo(@PathVariable Long codigo) {
		return this.categoriaRepository.findById(codigo).map(categoria -> ResponseEntity.ok(categoria))
				.orElse(ResponseEntity.notFound().build());
	}

	/***
	 **** Uma solução bem utilizada pode ser vista logo abaixo nessa solução é
	 * utilizado um isPresent pra mim é mais legível e menos funcional
	 **/

	// @GetMapping("/{codigo}")
	// public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo)
	// {
	// Optional<Categoria> categoria = this.categoriaRepository.findById(codigo);
	// return categoria.isPresent() ? ResponseEntity.ok(categoria.get()) :
	// ResponseEntity.notFound().build();
	// }
	@ApiOperation(value = "Adicionando Pessoa ao banco de dados com cotexto validation ")
	@PostMapping
	public ResponseEntity<Categoria> criarCategoria(@Valid @RequestBody Categoria categoria,
			HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);

		// agora eu preciso retornar para o location a minha url completa com o código
		// do que foi inserido

		this.publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));

		// URI uri =
		// ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
		// .buildAndExpand(categoriaSalva.getCodigo()).toUri();

		// de fato retorno o header
		// response.setHeader("location", uri.toASCIIString());

		// retorno por fim a categoria
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}

}
