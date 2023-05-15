package br.com.bsh.algafoodapi.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import br.com.bsh.algafoodapi.domain.entity.Cidade;
import br.com.bsh.algafoodapi.domain.repository.CidadeRepository;

@Repository
public class CidadeRepositoryImpl implements CidadeRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Cidade> listar() {
		return entityManager.createQuery("from Cidade", Cidade.class).getResultList();
	}

	@Override
	public Cidade buscar(Long id) {
		return entityManager.find(Cidade.class, id);
	}

	@Transactional
	@Override
	public Cidade salvar(Cidade cidade) {
		return entityManager.merge(cidade);
	}

	@Transactional
	@Override
	public void remover(Long id) {
		Cidade cidade = buscar(id);

		if (cidade == null) {
			throw new EmptyResultDataAccessException(1);
		}

		entityManager.remove(cidade);
	}

}
