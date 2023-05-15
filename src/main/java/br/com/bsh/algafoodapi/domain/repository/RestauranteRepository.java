package br.com.bsh.algafoodapi.domain.repository;

import java.util.List;

import br.com.bsh.algafoodapi.domain.entity.Restaurante;

public interface RestauranteRepository {

	List<Restaurante> listar();
	Restaurante buscar(Long id);
	Restaurante salvar(Restaurante restaurante);
	void remover(Long id);
	
}
