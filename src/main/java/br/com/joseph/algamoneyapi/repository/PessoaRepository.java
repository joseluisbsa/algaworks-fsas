package br.com.joseph.algamoneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.joseph.algamoneyapi.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
