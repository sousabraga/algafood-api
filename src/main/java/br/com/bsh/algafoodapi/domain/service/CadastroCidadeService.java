package br.com.bsh.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.bsh.algafoodapi.domain.entity.Cidade;
import br.com.bsh.algafoodapi.domain.entity.Estado;
import br.com.bsh.algafoodapi.domain.exception.EntidadeEmUsoException;
import br.com.bsh.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.bsh.algafoodapi.domain.repository.CidadeRepository;
import br.com.bsh.algafoodapi.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;
    
    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade) {
    	Long estadoId = cidade.getEstado().getId();
    	
    	Estado estado = estadoRepository.buscar(estadoId);
    	
    	if (estado == null) {
    		throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de estado com código %d", estadoId));
    	}
    	
    	cidade.setEstado(estado);
    	
        return cidadeRepository.salvar(cidade);
    }

    public void remover(Long id) {
        try {
        	cidadeRepository.remover(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cidade com código %d", id));
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(String.format("Cidade de código %d não pode ser removida, pois está em uso", id));
        }
    }

}
