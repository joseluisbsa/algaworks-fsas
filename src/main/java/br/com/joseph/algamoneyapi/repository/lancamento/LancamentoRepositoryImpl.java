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

import br.com.joseph.algamoneyapi.model.Lancamento;
import br.com.joseph.algamoneyapi.model.Lancamento_;
import br.com.joseph.algamoneyapi.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Lancamento> filtrar(LancamentoFilter filter) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		// cria as restriçoes
		Predicate[] predicates = criaRestricoes(filter, builder, root);
		criteria.where(predicates);

		TypedQuery<Lancamento> query = em.createQuery(criteria);
		return query.getResultList();
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

		return null;
	}

}
