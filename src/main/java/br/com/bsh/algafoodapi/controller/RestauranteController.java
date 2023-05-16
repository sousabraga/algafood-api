package br.com.bsh.algafoodapi.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bsh.algafoodapi.domain.entity.Restaurante;
import br.com.bsh.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.bsh.algafoodapi.domain.repository.RestauranteRepository;
import br.com.bsh.algafoodapi.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
    private RestauranteRepository restauranteRepository;
	
	@Autowired
    private CadastroRestauranteService cadastroRestaurante;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurante> listar() {
        return restauranteRepository.listar();
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
		Restaurante restaurante = restauranteRepository.buscar(id);
        return restaurante == null? ResponseEntity.notFound().build() : ResponseEntity.ok(restaurante);
    }
	
	@PostMapping
    public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = cadastroRestaurante.salvar(restaurante);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
        
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<Restaurante> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
		Restaurante restauranteAtual = restauranteRepository.buscar(id);

        if (restauranteAtual == null) {
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
        return ResponseEntity.ok(cadastroRestaurante.salvar(restauranteAtual));
    }
	
	@PatchMapping("/{id}")
    public ResponseEntity<Restaurante> atualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
		Restaurante restauranteAtual = restauranteRepository.buscar(id);
		
		if (restauranteAtual == null) {
            return ResponseEntity.notFound().build();
        }

		setarCamposParaAlterar(campos, restauranteAtual);
		
        return atualizar(id, restauranteAtual);
    }

	private void setarCamposParaAlterar(Map<String, Object> camposParaAlterar, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();

		Restaurante restauranteVO = objectMapper.convertValue(camposParaAlterar, Restaurante.class);
		
		camposParaAlterar.forEach((nomePropriedade, valorPropriedade) -> {
			Field campo = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			campo.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(campo, restauranteVO);
			
			ReflectionUtils.setField(campo, restauranteDestino, novoValor);
		});
		
	}
	
	
}
