package br.com.joseph.algamoneyapi.repository.lancamento;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.joseph.algamoneyapi.model.Lancamento;
import br.com.joseph.algamoneyapi.model.Lancamento_;
import br.com.joseph.algamoneyapi.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		// cria as restriçoes
		Predicate[] predicates = criaRestricoes(filter, builder, root);
		criteria.where(predicates);

		TypedQuery<Lancamento> query = em.createQuery(criteria);
		adicionarRestricoesDePaginacaoNaQuery(query, pageable);
		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}

	private Predicate[] criaRestricoes(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root) {

		List<Predicate> p = new ArrayList<>();

		if (nonNull(filter.getDescricao())) {
			p.add(builder.like(builder.lower(root.get(Lancamento_.descricao)),
					"%" + filter.getDescricao().toLowerCase() + "%"));
		}
		if (nonNull(filter.getDataVencimentoDe())) {
			p.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoDe()));
		}

		if (nonNull(filter.getDataVencimentoAte())) {
			p.add(builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoAte()));
		}

		return p.toArray(new Predicate[p.size()]);
	}

	private void adicionarRestricoesDePaginacaoNaQuery(TypedQuery<Lancamento> query, Pageable pageable) {

		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

	}

	private Long total(LancamentoFilter filter) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		Predicate[] p = criaRestricoes(filter, builder, root);
		criteria.where(p);

		criteria.select(builder.count(root));
		return em.createQuery(criteria).getSingleResult();
	}
}
