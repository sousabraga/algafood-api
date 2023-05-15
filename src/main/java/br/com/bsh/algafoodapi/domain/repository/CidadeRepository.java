package br.com.bsh.algafoodapi.domain.repository;

import java.util.List;

import br.com.bsh.algafoodapi.domain.entity.Cidade;

public interface CidadeRepository {
	
	List<Cidade> listar();
	Cidade buscar(Long id);
	Cidade salvar(Cidade estado);
	void remover(Long id);

}
