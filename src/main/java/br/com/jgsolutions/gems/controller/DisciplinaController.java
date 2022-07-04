package br.com.jgsolutions.gems.controller;

import br.com.jgsolutions.gems.exception.BadResourceException;
import br.com.jgsolutions.gems.exception.ResourceAlreadyExistsException;
import br.com.jgsolutions.gems.exception.ResourceNotFoundException;
import br.com.jgsolutions.gems.model.Disciplina;
import br.com.jgsolutions.gems.service.DisciplinaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;


@RestController
@RequestMapping("/gems/disciplina")
//@Tag(name = "disciplina", description = "API para CRUD de disciplina")
public class DisciplinaController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DisciplinaService disciplinaService;

    @GetMapping(value = "/", consumes =
            MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Disciplina>> findAll(
            @RequestBody(required=false) String nome, Pageable pageable){
        if (StringUtils.isEmpty(nome)) {
            return ResponseEntity.ok(disciplinaService.findAll(pageable));
        }
        else {
            return ResponseEntity.ok(disciplinaService.findAllByNome(nome, pageable));
        }
    }

    @GetMapping(value = "/{id}", produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Disciplina> findDisciplinaById(@PathVariable long id) {
        try {
            Disciplina disciplina = disciplinaService.findById(id);
            return ResponseEntity.ok(disciplina);
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @PostMapping(value = "/")
    public ResponseEntity<Disciplina> addDisciplina(@RequestBody Disciplina disciplina)
            throws URISyntaxException {
        try {
            Disciplina novoDisciplina = disciplinaService.save(disciplina);
            return ResponseEntity.created(new URI("/disciplina/" + novoDisciplina.getId())).body(disciplina);
        } catch (ResourceAlreadyExistsException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (BadResourceException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Disciplina> updateDisciplina(@Valid @RequestBody Disciplina disciplina, @PathVariable long id) {
        try {
            disciplina.setId(id);
            disciplinaService.update(disciplina);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (BadResourceException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteDisciplinaById(@PathVariable long id){
        try {
            disciplinaService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch(ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }
}
