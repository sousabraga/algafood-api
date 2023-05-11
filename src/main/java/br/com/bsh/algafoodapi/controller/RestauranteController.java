package br.com.bsh.algafoodapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bsh.algafoodapi.domain.entity.Restaurante;
import br.com.bsh.algafoodapi.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
    private RestauranteRepository restauranteRepository;

	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurante> listar() {
        return restauranteRepository.listar();
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
		Restaurante restaurante = restauranteRepository.buscar(id);
        return restaurante == null? ResponseEntity.notFound().build() : ResponseEntity.ok(restaurante);
    }
	
}
