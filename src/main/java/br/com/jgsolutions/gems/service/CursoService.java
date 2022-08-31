package br.com.jgsolutions.gems.service;

import br.com.jgsolutions.gems.exception.BadResourceException;
import br.com.jgsolutions.gems.exception.ResourceAlreadyExistsException;
import br.com.jgsolutions.gems.exception.ResourceNotFoundException;
import br.com.jgsolutions.gems.model.Curso;
import br.com.jgsolutions.gems.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    private boolean existsById(Long id) {
        return cursoRepository.existsById(id);
    }

    public Curso findById(Long id) throws ResourceNotFoundException {
        Curso curso = cursoRepository.findById(id).orElse(null);
        if (curso == null) {
            throw new ResourceNotFoundException("Curso não encontrado com o id: " + id);
        } else return curso;
    }

    public Page<Curso> findAll(Pageable pageable) {
        return cursoRepository.findAll(pageable);
    }

    public Page<Curso> findAllByNome(String nome, Pageable page) {
        Page<Curso> cursos = cursoRepository.findByNome(nome, page);
        return cursos;
    }

    public Curso save(Curso curso) throws BadResourceException, ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(curso.getNomeCurso())) {
            if (curso.getId() != null && existsById(curso.getId())) {
                throw new ResourceAlreadyExistsException("Curso com id: " + curso.getId() + " já existe");
            }
            return cursoRepository.save(curso);
        } else {
            BadResourceException exc = new BadResourceException("Erro ao salvar o curso");
            exc.addErrorMessage("Curso está vazio ou é nulo");
            throw exc;
        }
    }

    public void update(Curso curso) throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(curso.getNomeCurso())) {
            if (!existsById(curso.getId())) {
                throw new ResourceNotFoundException("Curso não encontrado com o id: " + curso.getId());
            }
            cursoRepository.save(curso);
        } else {
            BadResourceException exc = new BadResourceException("Falha ao salvar o curso");
            exc.addErrorMessage("Curso está nulo ou em branco");
            throw exc;
        }
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("Curso não encontrado com o id: " + id);
        } else {
            cursoRepository.deleteById(id);
        }
    }

    public Long count() {
        return cursoRepository.count();
    }

}
