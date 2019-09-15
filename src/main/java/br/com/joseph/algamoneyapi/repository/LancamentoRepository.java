package br.com.joseph.algamoneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.joseph.algamoneyapi.model.Lancamento;
import br.com.joseph.algamoneyapi.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
