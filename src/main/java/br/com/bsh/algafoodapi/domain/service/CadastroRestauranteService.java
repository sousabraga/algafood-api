package br.com.bsh.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.bsh.algafoodapi.domain.entity.Cozinha;
import br.com.bsh.algafoodapi.domain.entity.Restaurante;
import br.com.bsh.algafoodapi.domain.exception.EntidadeEmUsoException;
import br.com.bsh.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.bsh.algafoodapi.domain.repository.CozinhaRepository;
import br.com.bsh.algafoodapi.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;
    
    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
    	Long cozinhaId = restaurante.getCozinha().getId();
    	
    	Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
    	
    	if (cozinha == null) {
    		throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cozinha com código %d", cozinhaId));
    	}
    	
    	restaurante.setCozinha(cozinha);
    	
        return restauranteRepository.salvar(restaurante);
    }

    public void remover(Long id) {
        try {
        	restauranteRepository.remover(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de restaurante com código %d", id));
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(String.format("Restaurante de código %d não pode ser removido, pois está em uso", id));
        }
    }

}
