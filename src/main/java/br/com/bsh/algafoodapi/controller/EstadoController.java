package br.com.bsh.algafoodapi.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.bsh.algafoodapi.domain.entity.Estado;
import br.com.bsh.algafoodapi.domain.exception.EntidadeEmUsoException;
import br.com.bsh.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.bsh.algafoodapi.domain.repository.EstadoRepository;
import br.com.bsh.algafoodapi.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;
    
    @Autowired
    private CadastroEstadoService cadastroEstado;

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.listar();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Estado> buscar(@PathVariable Long id) {
    	Estado estado = estadoRepository.buscar(id);
        return estado == null? ResponseEntity.notFound().build() : ResponseEntity.ok(estado);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado salvar(@RequestBody Estado estado) {
        return cadastroEstado.salvar(estado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> atualizar(@PathVariable Long id, @RequestBody Estado estado) {
    	Estado estadoAtual = estadoRepository.buscar(id);

        if (estadoAtual == null) {
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(estado, estadoAtual, "id");
        return ResponseEntity.ok(cadastroEstado.salvar(estadoAtual));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Estado> remover(@PathVariable Long id) {
        try {
        	cadastroEstado.remover(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
