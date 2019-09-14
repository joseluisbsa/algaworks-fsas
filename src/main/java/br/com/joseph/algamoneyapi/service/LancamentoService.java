package br.com.joseph.algamoneyapi.service;

import static java.util.Objects.isNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joseph.algamoneyapi.exception.PessoaInexistenteOuInativaException;
import br.com.joseph.algamoneyapi.model.Lancamento;
import br.com.joseph.algamoneyapi.model.Pessoa;
import br.com.joseph.algamoneyapi.repository.LancamentoRepository;
import br.com.joseph.algamoneyapi.repository.PessoaRepository;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Lancamento save(Lancamento lancamento) {
		
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getId());
		if(isNull(pessoa) || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return repository.save(lancamento);
	}

	
	
}
