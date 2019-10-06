package br.com.joseph.algamoneyapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joseph.algamoneyapi.event.RecursoCriadoEvent;
import br.com.joseph.algamoneyapi.model.Categoria;
import br.com.joseph.algamoneyapi.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository repository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping
	// alem da permissao do usuario tem a permissao da aplicação tambem
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Categoria> save(@Valid @RequestBody Categoria categoria, HttpServletResponse res) {
		Categoria c = repository.save(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, res, c.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(c);
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public List<Categoria> findAll() {
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<Categoria> findById(@PathVariable Long id) {
		return repository.findById(id)
				.map(c -> ResponseEntity.ok(c))
				.orElse(ResponseEntity.notFound().build());
	}	

}