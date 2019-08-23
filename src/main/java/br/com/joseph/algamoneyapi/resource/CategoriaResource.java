package br.com.joseph.algamoneyapi.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.joseph.algamoneyapi.model.Categoria;
import br.com.joseph.algamoneyapi.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository repository;
	
	@PostMapping
	public ResponseEntity<Categoria> save(@RequestBody Categoria categoria, HttpServletResponse res) {
		Categoria c = repository.save(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(c.getId())
				.toUri();
		res.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(c);
	}
	
	@GetMapping
	public List<Categoria> findAll() {
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> findById(@PathVariable Long id) {
		return repository.findById(id)
				.map(c -> ResponseEntity.ok(c))
				.orElse(ResponseEntity.notFound().build());
	}	

}