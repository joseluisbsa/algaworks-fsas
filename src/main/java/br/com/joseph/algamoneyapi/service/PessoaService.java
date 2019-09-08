package br.com.joseph.algamoneyapi.service;

import static java.util.Objects.isNull;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.joseph.algamoneyapi.model.Pessoa;
import br.com.joseph.algamoneyapi.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;

	public Pessoa atualizar(Long id, Pessoa pessoa) {
		Pessoa pessoaSalva = findById(id);
		
		BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
		return repository.save(pessoaSalva);
	}

	private Pessoa findById(Long id) {
		Pessoa pessoa = repository.findOne(id);
		if(isNull(pessoa)) 
			throw new EmptyResultDataAccessException(1);
		 
		return pessoa;
	}

	public void atualizarPropAtivo(Long id, Boolean ativo) {
		Pessoa pessoa = findById(id);
		pessoa.setAtivo(ativo);
		repository.save(pessoa);
	}
}
