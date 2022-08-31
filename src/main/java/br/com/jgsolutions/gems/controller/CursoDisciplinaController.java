package br.com.jgsolutions.gems.controller;

import br.com.jgsolutions.gems.exception.BadResourceException;
import br.com.jgsolutions.gems.exception.ResourceAlreadyExistsException;
import br.com.jgsolutions.gems.exception.ResourceNotFoundException;
import br.com.jgsolutions.gems.model.CursoDisciplina;
import br.com.jgsolutions.gems.service.CursoDisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/gems/cursoDisciplina")
public class CursoDisciplinaController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CursoDisciplinaService cursoDisciplinaService;

    @GetMapping(value="/")
    public ResponseEntity<Page<CursoDisciplina>> findAll(Pageable pageable){
        return ResponseEntity.ok(cursoDisciplinaService.findAll(pageable));
    }

    @PostMapping(value="/")
    public ResponseEntity<CursoDisciplina> addCursoDisciplina(@RequestBody CursoDisciplina cursoDisciplina) throws URISyntaxException {
        try {
            CursoDisciplina p = cursoDisciplinaService.save(cursoDisciplina);
            return ResponseEntity.created(new URI("/gems/cursoDisciplina/"+p.getId())).body(cursoDisciplina);
        }catch(ResourceAlreadyExistsException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }catch(BadResourceException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<CursoDisciplina> updateCursoDisciplina(@Valid @RequestBody CursoDisciplina cursoDisciplina, @PathVariable long id) throws BadResourceException{
        try {
            cursoDisciplina.setId(id);
            cursoDisciplinaService.update(cursoDisciplina);
            return ResponseEntity.ok().build();
        }catch(ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }catch(BadResourceException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<CursoDisciplina> deleteCursoDisciplinaById(@PathVariable long id){
        try {
            cursoDisciplinaService.deleteById(id);
            return ResponseEntity.ok().build();
        }catch(ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }
}