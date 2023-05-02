package br.com.bsh.algafoodapi.controller;

import br.com.bsh.algafoodapi.domain.entity.Cozinha;
import br.com.bsh.algafoodapi.domain.repository.CozinhaRepository;
import br.com.bsh.algafoodapi.model.CozinhasXmlWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cozinha> listar() {
        return cozinhaRepository.listar();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWrapper listarXml() {
        return new CozinhasXmlWrapper(cozinhaRepository.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
        Cozinha cozinha = cozinhaRepository.buscar(id);
        return cozinha == null? ResponseEntity.notFound().build() : ResponseEntity.ok(cozinha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha salvar(@RequestBody Cozinha cozinha) {
        return cozinhaRepository.salvar(cozinha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
        Cozinha cozinhaAtual = cozinhaRepository.buscar(id);

        if (cozinhaAtual == null) {
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
        return ResponseEntity.ok(cozinhaRepository.salvar(cozinhaAtual));
    }

}
