package br.com.jgsolutions.gems.service;

import br.com.jgsolutions.gems.exception.BadResourceException;
import br.com.jgsolutions.gems.exception.ResourceAlreadyExistsException;
import br.com.jgsolutions.gems.exception.ResourceNotFoundException;
import br.com.jgsolutions.gems.model.Professor;
import br.com.jgsolutions.gems.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    private boolean existsById(Long id){
        return professorRepository.existsById(id);
    }

    public Professor findById(Long id) throws ResourceNotFoundException {
        Professor professor = professorRepository.findById(id).orElse(null);
        if(professor == null) {
            throw new ResourceNotFoundException("Professor não encontrado com o id: " + id);
        }
        else return professor;
    }

    public Page<Professor> findAll(Pageable pageable) {
        return professorRepository.findAll(pageable);
    }

    public Page<Professor> findAllByNome(String nome, Pageable page) {
        Page<Professor> professors = professorRepository.findByNome(nome, page);
        return professors;
    }

    public Professor save(Professor professor) throws BadResourceException, ResourceAlreadyExistsException {
        if(!StringUtils.isEmpty(professor.getNomeProfessor())) {
            if (professor.getId() != null && existsById(professor.getId())) {
                throw new ResourceAlreadyExistsException("Professor com id: " + professor.getId() + " já existe");
            }
            return professorRepository.save(professor);
        }
        else {
            BadResourceException exc = new BadResourceException("Erro ao salvar o professor");
            exc.addErrorMessage("Professor está vazio ou é nulo");
            throw exc;
        }
    }

    public void update(Professor professor) throws BadResourceException, ResourceNotFoundException{
        if (!StringUtils.isEmpty(professor.getNomeProfessor())){
            if (!existsById(professor.getId())){
                throw  new ResourceNotFoundException("Professor não encontrado com o id: " + professor.getId());
            }
            professorRepository.save(professor);
        }
        else {
            BadResourceException exc = new BadResourceException("Falha ao salvar o professor");
            exc.addErrorMessage("Professor está nulo ou em branco");
            throw exc;
        }
    }

    public void deleteById(Long id) throws  ResourceNotFoundException {
        if (!existsById(id)) {
            throw  new ResourceNotFoundException("Professor não encontrado com o id: " + id);
        }
        else{
            professorRepository.deleteById(id);
        }
    }

    public Long count() {
        return professorRepository.count();
    }

}
