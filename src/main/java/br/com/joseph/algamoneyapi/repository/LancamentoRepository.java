package br.com.joseph.algamoneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.joseph.algamoneyapi.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
