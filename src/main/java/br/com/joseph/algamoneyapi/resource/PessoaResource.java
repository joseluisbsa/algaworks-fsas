package br.com.joseph.algamoneyapi.resource;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.joseph.algamoneyapi.model.Pessoa;
import br.com.joseph.algamoneyapi.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository repository;

	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = repository.save(pessoa);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(pessoaSalva.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());

		return ResponseEntity.created(uri).body(pessoaSalva);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long id) {
		Pessoa pessoa = repository.findOne(id);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}

}