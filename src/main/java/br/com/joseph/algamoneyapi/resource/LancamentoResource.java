package br.com.joseph.algamoneyapi.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joseph.algamoneyapi.model.Lancamento;
import br.com.joseph.algamoneyapi.repository.LancamentoRepository;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository repository;

	@GetMapping
	public List<Lancamento> listar() {
		return repository.findAll();
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
		Lancamento lancamento = repository.findOne(codigo);
		return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}

}
