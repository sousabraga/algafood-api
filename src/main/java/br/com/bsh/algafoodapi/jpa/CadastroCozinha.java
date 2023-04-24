package br.com.bsh.algafoodapi.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.bsh.algafoodapi.domain.entity.Cozinha;

@Component
public class CadastroCozinha {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Cozinha> listar() {
		return entityManager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}
	
	public Cozinha buscarPorId(Long id) {
		return entityManager.find(Cozinha.class, id);
	}
	
	@Transactional
	public Cozinha adicionar(Cozinha cozinha) {
		return entityManager.merge(cozinha);
	}
	
}
