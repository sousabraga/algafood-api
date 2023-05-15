package br.com.bsh.algafoodapi.domain.repository;

import java.util.List;

import br.com.bsh.algafoodapi.domain.entity.Estado;

public interface EstadoRepository {

    List<Estado> listar();
    Estado buscar(Long id);
    Estado salvar(Estado estado);
	void remover(Long id);

}
