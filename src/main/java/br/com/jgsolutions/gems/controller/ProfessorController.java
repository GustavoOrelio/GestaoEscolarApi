package br.com.jgsolutions.gems.controller;

import br.com.jgsolutions.gems.exception.BadResourceException;
import br.com.jgsolutions.gems.exception.ResourceAlreadyExistsException;
import br.com.jgsolutions.gems.exception.ResourceNotFoundException;
import br.com.jgsolutions.gems.model.Professor;
import br.com.jgsolutions.gems.service.ProfessorService;

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
@RequestMapping("/gems/professor")
//@Tag(name = "professor", description = "API para CRUD de professor")
public class ProfessorController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProfessorService professorService;

    @GetMapping(value = "/", consumes =
            MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Professor>> findAll(
            @RequestBody(required=false) String nome, Pageable pageable){
        if (StringUtils.isEmpty(nome)) {
            return ResponseEntity.ok(professorService.findAll(pageable));
        }
        else {
            return ResponseEntity.ok(professorService.findAllByNome(nome, pageable));
        }
    }

    @GetMapping(value = "/{id}", produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Professor> findProfessorById(@PathVariable long id) {
        try {
            Professor professor = professorService.findById(id);
            return ResponseEntity.ok(professor);
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @PostMapping(value = "/")
    public ResponseEntity<Professor> addProfessor(@RequestBody Professor professor)
            throws URISyntaxException {
        try {
            Professor novoProfessor = professorService.save(professor);
            return ResponseEntity.created(new URI("/professor/" + novoProfessor.getId())).body(professor);
        } catch (ResourceAlreadyExistsException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (BadResourceException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Professor> updateProfessor(@Valid @RequestBody Professor professor, @PathVariable long id) {
        try {
            professor.setId(id);
            professorService.update(professor);
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
    public ResponseEntity<Void> deleteProfessorById(@PathVariable long id){
        try {
            professorService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch(ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }
}
