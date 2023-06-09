package br.com.bsh.algafoodapi.domain.repository;

import java.util.List;

import br.com.bsh.algafoodapi.domain.entity.Cozinha;

public interface CozinhaRepository {

	List<Cozinha> listar();
	Cozinha buscar(Long id);
	Cozinha salvar(Cozinha cozinha);
	void remover(Long id);
}
