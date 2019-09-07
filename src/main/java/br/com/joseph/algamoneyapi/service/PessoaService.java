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
		Pessoa pessoaSalva = repository.findOne(id);
		
		if(isNull(pessoaSalva)) 
			throw new EmptyResultDataAccessException(1);
		
		BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
		return repository.save(pessoaSalva);
	}
}
